package com.ge.predix.labs.integrationtest.cucumber;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(format = { "pretty", "html:target/cucumber" }, features = { "src/test/resources/Cucumber" },
/**
if you want to run one feature only,
annotate your feature file with @RunJustThisTest and un-comment this line

 **/
//tags = { "@RunJustThisTest" },
dryRun = false)
public class RunCucumberTest {

}