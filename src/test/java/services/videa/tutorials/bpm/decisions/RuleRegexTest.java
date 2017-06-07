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
package services.videa.tutorials.bpm.decisions;

import static org.junit.Assert.assertEquals;

import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

@Deployment(resources = { "decisions/rule-regex.dmn" })
public class RuleRegexTest {

	@Rule
	public ProcessEngineRule processEngine = new ProcessEngineRule();

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		VariableMap variables = Variables.createVariables()
				.putValue("rechtsform", "N")
				.putValue("anrede", "x")
				.putValue("vorname", "Oli")
				.putValue("familienname", "Hock")
				.putValueTyped("geburtsdatum", Variables.stringValue("19710813"))
				.putValue("geburtsort", "Beckum")
				.putValue("firmenname", "Videa")
				.putValue("vereinigung", "")
				.putValue("selbstaendig", true)
				.putValue("beruf", "Softwareentwickler")
				;

		DmnDecisionTableResult decisionResult = processEngine.getDecisionService()
				.evaluateDecisionTableByKey("rule_regex", variables);

		assertEquals(true, decisionResult.getSingleResult().getEntry("ergebnis"));
		assertEquals("0002", decisionResult.getSingleResult().getEntry("nachricht"));
	}

}
