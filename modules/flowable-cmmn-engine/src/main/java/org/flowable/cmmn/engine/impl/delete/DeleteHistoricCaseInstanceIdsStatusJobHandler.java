/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.flowable.cmmn.engine.impl.delete;

import org.flowable.batch.api.Batch;
import org.flowable.batch.api.BatchPartQuery;
import org.flowable.cmmn.api.CmmnManagementService;
import org.flowable.cmmn.engine.CmmnEngineConfiguration;
import org.flowable.cmmn.engine.impl.util.CommandContextUtil;
import org.flowable.common.engine.api.FlowableIllegalArgumentException;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.job.service.JobHandler;
import org.flowable.job.service.impl.persistence.entity.JobEntity;
import org.flowable.variable.api.delegate.VariableScope;

/**
 * @author Filip Hrisafov
 */
public class DeleteHistoricCaseInstanceIdsStatusJobHandler implements JobHandler {

    public static final String TYPE = "delete-historic-case-status";

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public void execute(JobEntity job, String configuration, VariableScope variableScope, CommandContext commandContext) {
        CmmnEngineConfiguration engineConfiguration = CommandContextUtil.getCmmnEngineConfiguration(commandContext);
        CmmnManagementService managementService = engineConfiguration.getCmmnManagementService();
        Batch batch = managementService.createBatchQuery()
                .batchId(configuration)
                .singleResult();

        if (batch == null) {
            throw new FlowableIllegalArgumentException("There is no batch with the id " + configuration);
        }

        long totalBatchParts = createStatusQuery(batch, managementService).count();
        long totalCompleted = createStatusQuery(batch, managementService).completed().count();

        if (totalBatchParts == totalCompleted) {
            long totalFailed = createStatusQuery(batch, managementService)
                    .status(DeleteCaseInstanceBatchConstants.STATUS_FAILED)
                    .count();

            if (totalFailed == 0) {
                completeBatch(batch, DeleteCaseInstanceBatchConstants.STATUS_COMPLETED, engineConfiguration);
            } else {
                completeBatch(batch, DeleteCaseInstanceBatchConstants.STATUS_FAILED, engineConfiguration);
            }

            job.setRepeat(null);
        } else if (totalBatchParts == 0) {
            completeBatch(batch, DeleteCaseInstanceBatchConstants.STATUS_COMPLETED, engineConfiguration);
            job.setRepeat(null);
        }

    }

    protected BatchPartQuery createStatusQuery(Batch batch, CmmnManagementService managementService) {
        return managementService.createBatchPartQuery()
                .batchId(batch.getId())
                .type(DeleteCaseInstanceBatchConstants.BATCH_PART_DELETE_CASE_INSTANCES_TYPE);
    }

    protected void completeBatch(Batch batch, String status, CmmnEngineConfiguration engineConfiguration) {
        engineConfiguration.getBatchServiceConfiguration()
                .getBatchService()
                .completeBatch(batch.getId(), status);
    }
}
