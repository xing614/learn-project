package demo.entity;

import java.io.Serializable;

public class Permission implements Serializable{
    private Long id;

    private String url;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
    
	@Override
	public String toString (){
		return "Permission[id="+id
		+",url="+url
		+",name="+name+"]";
	}
}