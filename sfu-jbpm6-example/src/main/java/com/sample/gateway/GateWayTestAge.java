package com.sample.gateway;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.jbpm.process.core.context.variable.VariableScope;
import org.jbpm.process.instance.ProcessInstance;
import org.jbpm.process.instance.context.variable.VariableScopeInstance;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class GateWayTestAge {

    public static void main(String[] args) {
        
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        KieBase kieBase = kContainer.getKieBase("kbase-gateway");
        KieSession ksession = kieBase.newKieSession();
        
        Person sfu = new Person();
        sfu.setId("001");
        
        // Note the "Priority" setup on the gateway in "gateway-exclusive-testage.bpmn"
        // change this age value and test again
        sfu.setAge(10);
        sfu.setName("Shilong");
        sfu.setIncome(new BigDecimal(50000.00d));
        sfu.setProcessedBy("");
        
        // pass in parameter
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("person", sfu);
         
        // start a new process instance
        ProcessInstance processInstance = (ProcessInstance) ksession.startProcess("com.sample.gateway.exclusive.testage",params);
        
        
        // Get variables back
        VariableScopeInstance variableScope = (VariableScopeInstance) processInstance.getContextInstance(VariableScope.VARIABLE_SCOPE);

        Map<String, Object> variables = variableScope.getVariables();
        Person objAfterProcess = (Person) variables.get("person");
        
        System.out.print("From client code, Processed by: "+objAfterProcess.getProcessedBy());
        
        
        ksession.dispose();
        System.exit(0);
    }


}
