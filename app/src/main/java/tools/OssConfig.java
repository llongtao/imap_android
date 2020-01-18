package tools;

public class OssConfig {
    private String accessid="";
    private String signature="=";
    private String expire="";
    private String host="";
    private String dir="/";
    private String policy="=";
    public OssConfig(String accessid, String signature, String expire, String host, String dir, String policy) {
          this.accessid=accessid;
          this.signature=signature;
          this.expire=expire;
          this.host=host;
          this.dir=dir;
          this.policy=policy;
    }

    public String getAccessid() {
        return accessid;
    }

    public String getSignature() {
        return signature;
    }

    public String getExpire() {
        return expire;
    }

    public String getHost() {
        return host;
    }

    public String getDir() {
        return dir;
    }

    public String getPolicy() {
        return policy;
    }

    public void setAccessid(String accessid) {
        this.accessid = accessid;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public void setExpire(String expire) {
        this.expire = expire;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

}
