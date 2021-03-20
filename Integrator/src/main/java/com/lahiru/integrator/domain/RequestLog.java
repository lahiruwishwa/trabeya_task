package com.lahiru.integrator.domain;


import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "request_log")
public class RequestLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "time")
    private Date time;

    @Column(name = "http_status")
    private int httpStatus;

    @Column(name = "http_method")
    @Size(max = 6)
    private String httpMethod;

    @Column(name = "path")
    @Size(max = 100)
    private String path;

    @Column(name = "request_body")
    @Size(max = 1000)
    private String requestBody;

    @Column(name = "ip")
    @Size(max = 45)
    private String clientIp;

    @Column(name = "response")
    @Size(max = 1000)
    private String response;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", time=" + time +
                ", httpStatus=" + httpStatus +
                ", httpMethod='" + httpMethod + '\'' +
                ", path='" + path + '\'' +
                ", requestBody='" + requestBody + '\'' +
                ", clientIp='" + clientIp + '\'' +
                ", response='" + response + '\'' +
                '}';
    }
}
