/*
 Copyright (c) 2017 Videa Project Services GmbH

 Permission is hereby granted, free of charge, to any person obtaining a copy of this software 
 and associated documentation files (the "Software"), to deal in the Software without restriction, 
 including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 and/or sell copies of the Software,and to permit persons to whom the Software is furnished to do so, 
 subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all copies or substantial 
 portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT 
 NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. 
 IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, 
 WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE 
 SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package services.videa.tutorials.bpm.processes;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions.assertThat;
import static org.junit.Assert.assertEquals;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

@Deployment(resources = { 
		"processes/process-variables-main.bpmn",
		"processes/process-variables-subprocess.bpmn"
		})
public class ProcessVariablesTest {

	@Rule
	public ProcessEngineRule processEngine = new ProcessEngineRule();

	private RuntimeService runtimeService = null;
	@SuppressWarnings("unused")
	private TaskService taskService = null;

	@Before
	public void setUp() throws Exception {
		runtimeService = processEngine.getRuntimeService();
		taskService = processEngine.getTaskService();
	}

	@Test
	public void throwErrorEvent() {
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("Process_ProcessVariablesMain");
		assertThat(processInstance).isActive();
		
		Task task = taskService.createTaskQuery().taskName("Check Variables").singleResult();
		assertEquals("Check Variables", task.getName());

		assertThat(processInstance).isNotEnded();
		
		taskService.complete(task.getId());
		assertThat(processInstance).isEnded();
	}

}
