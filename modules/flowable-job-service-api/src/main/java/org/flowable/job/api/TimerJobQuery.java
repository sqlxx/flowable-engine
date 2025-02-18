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

package org.flowable.job.api;

import java.util.Date;

import org.flowable.common.engine.api.query.Query;

/**
 * Allows programmatic querying of {@link Job}s.
 * 
 * @author Joram Barrez
 * @author Tijs Rademakers
 */
public interface TimerJobQuery extends Query<TimerJobQuery, Job> {

    /** Only select jobs with the given id */
    TimerJobQuery jobId(String jobId);

    /** Only select jobs which exist for the given process instance. **/
    TimerJobQuery processInstanceId(String processInstanceId);
    
    /** Only select jobs without a process instance id value. **/
    TimerJobQuery withoutProcessInstanceId();

    /** Only select jobs which exist for the given execution */
    TimerJobQuery executionId(String executionId);

    /** Only select jobs which exist for the given process definition id */
    TimerJobQuery processDefinitionId(String processDefinitionid);
    
    /** Only select jobs which exist for the given category */
    TimerJobQuery category(String category);
    
    /** Only select jobs like for the given category value */
    TimerJobQuery categoryLike(String categoryLike);
    
    /** Only select jobs which exist for the given element id */
    TimerJobQuery elementId(String elementId);
    
    /** Only select jobs which exist for the given element name */
    TimerJobQuery elementName(String elementName);
    
    /** Only select jobs for the given scope identifier. */
    TimerJobQuery scopeId(String scopeId);
    
    /** Only select jobs without a scope id value. **/
    TimerJobQuery withoutScopeId();

    /** Only select jobs for the given sub scope identifier. */
    TimerJobQuery subScopeId(String subScopeId);
    
    /** Only select jobs for the given scope type. */
    TimerJobQuery scopeType(String scopeType);

    /**
     * Only return jobs that do not have a scope type.
     */
    TimerJobQuery withoutScopeType();
    
    /** Only select tasks for the given scope definition identifier. */
    TimerJobQuery scopeDefinitionId(String scopeDefinitionId);
    
    /** Only select jobs for the given case instance. */
    TimerJobQuery caseInstanceId(String caseInstanceId);
    
    /** Only select jobs for the given case definition. */
    TimerJobQuery caseDefinitionId(String caseDefinitionId);
    
    /** Only select jobs for the given plan item instance.  */
    TimerJobQuery planItemInstanceId(String planItemInstanceId);

    /** Only select jobs with the given correlationId.  */
    TimerJobQuery correlationId(String correlationId);

    /**
     * Only select jobs which are executable, ie. duedate is null or duedate is in the past
     **/
    TimerJobQuery executable();

    /** Select jobs which have given job handler type */
    TimerJobQuery handlerType(String handlerType);

    /**
     * Only select jobs that are timers. Cannot be used together with {@link #messages()}
     */
    TimerJobQuery timers();

    /**
     * Only select jobs that are messages. Cannot be used together with {@link #timers()}
     */
    TimerJobQuery messages();

    /** Only select jobs where the duedate is lower than the given date. */
    TimerJobQuery duedateLowerThan(Date date);

    /** Only select jobs where the duedate is higher then the given date. */
    TimerJobQuery duedateHigherThan(Date date);

    /** Only select jobs that failed due to an exception. */
    TimerJobQuery withException();

    /** Only select jobs that failed due to an exception with the given message. */
    TimerJobQuery exceptionMessage(String exceptionMessage);

    /**
     * Only select jobs that have the given tenant id.
     */
    TimerJobQuery jobTenantId(String tenantId);

    /**
     * Only select jobs with a tenant id like the given one.
     */
    TimerJobQuery jobTenantIdLike(String tenantIdLike);

    /**
     * Only select jobs that do not have a tenant id.
     */
    TimerJobQuery jobWithoutTenantId();

    // sorting //////////////////////////////////////////

    /**
     * Order by job id (needs to be followed by {@link #asc()} or {@link #desc()}).
     */
    TimerJobQuery orderByJobId();

    /**
     * Order by duedate (needs to be followed by {@link #asc()} or {@link #desc()}).
     */
    TimerJobQuery orderByJobDuedate();

    /**
     * Order by create time (needs to be followed by {@link #asc()} or {@link #desc()}).
     */
    TimerJobQuery orderByJobCreateTime();

    /**
     * Order by retries (needs to be followed by {@link #asc()} or {@link #desc()}).
     */
    TimerJobQuery orderByJobRetries();

    /**
     * Order by process instance id (needs to be followed by {@link #asc()} or {@link #desc()}).
     */
    TimerJobQuery orderByProcessInstanceId();

    /**
     * Order by execution id (needs to be followed by {@link #asc()} or {@link #desc()}).
     */
    TimerJobQuery orderByExecutionId();

    /**
     * Order by tenant id (needs to be followed by {@link #asc()} or {@link #desc()}).
     */
    TimerJobQuery orderByTenantId();

}
