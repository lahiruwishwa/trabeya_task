package com.lahiru.integrator.config;

import com.lahiru.integrator.service.connector.AccountClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class SoapConfiguration {
    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.lahiru.integrator.wsdl");
        return marshaller;
    }

    @Bean
    public AccountClient accountClient(Jaxb2Marshaller marshaller) {
        AccountClient client = new AccountClient();
        client.setDefaultUri("http://localhost:8686/ws");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }
}
