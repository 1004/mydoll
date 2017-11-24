package com.fy.catchdoll.presentation.model.dto.updata;

public class UpdateDto {
	 public static final int FORCE_UPDATA = 1;
	    public static final int UN_FORCE_UPDATA = 0;
	    String version_name;
	    int version_code;
	    String size;
	    String desc;
	    int upgrade;
	    String pic;
	    String url;
	    String start_pic;

	    public UpdateDto() {
	        super();
	    }

	    public String getStart_pic() {
	        return start_pic;
	    }

	    public void setStart_pic(String start_pic) {
	        this.start_pic = start_pic;
	    }

	    public String getVersion_name() {
	        return version_name;
	    }

	    public void setVersion_name(String version_name) {
	        this.version_name = version_name;
	    }

	    public int getVersion_code() {
	        return version_code;
	    }

	    public void setVersion_code(int version_code) {
	        this.version_code = version_code;
	    }

	    public String getSize() {
	        return size;
	    }

	    public void setSize(String size) {
	        this.size = size;
	    }

	    public String getDesc() {
	        return desc;
	    }

	    public void setDesc(String desc) {
	        this.desc = desc;
	    }

	    public int getUpgrade() {
	        return upgrade;
	    }

	    public void setUpgrade(int upgrade) {
	        this.upgrade = upgrade;
	    }

	    public String getPic() {
	        return pic;
	    }

	    public void setPic(String pic) {
	        this.pic = pic;
	    }

	    public String getUrl() {
	        return url;
	    }

	    public void setUrl(String url) {
	        this.url = url;
	    }

	    @Override
	    public String toString() {
	        return "UpdateDto [version_name=" + version_name + ", version_code=" + version_code + ", size=" + size
	                + ", desc=" + desc + ", upgrade=" + upgrade + ", pic=" + pic + ", url=" + url + ", start_pic="
	                + start_pic + "]";
	    }
}
