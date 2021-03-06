package cn.blog.domain;

public class Web {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column web.web_id
     *
     * @mbg.generated Fri Nov 27 19:46:20 CST 2020
     */
    private Integer webId;
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column web.web_user_id
     *
     * @mbg.generated Fri Nov 27 19:46:20 CST 2020
     */
    private Integer webUserId;
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column web.web_is_open
     *
     * @mbg.generated Fri Nov 27 19:46:20 CST 2020
     */
    private Boolean webIsOpen;
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column web.web_domain
     *
     * @mbg.generated Fri Nov 27 19:46:20 CST 2020
     */
    private String webDomain;
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column web.web_name
     *
     * @mbg.generated Fri Nov 27 19:46:20 CST 2020
     */
    private String webName;

    public Web(Integer webId, Integer webUserId, Boolean webIsOpen, String webDomain, String webName) {
        this.webId = webId;
        this.webUserId = webUserId;
        this.webIsOpen = webIsOpen;
        this.webDomain = webDomain;
        this.webName = webName;
    }

    public Web() {
    }

    @Override
    public String toString() {
        return "Web{" +
                "webId=" + webId +
                ", webUserId=" + webUserId +
                ", webIsOpen=" + webIsOpen +
                ", webDomain='" + webDomain + '\'' +
                ", webName='" + webName + '\'' +
                '}';
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column web.web_id
     *
     * @return the value of web.web_id
     * @mbg.generated Fri Nov 27 19:46:20 CST 2020
     */
    public Integer getWebId() {
        return webId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column web.web_id
     *
     * @param webId the value for web.web_id
     * @mbg.generated Fri Nov 27 19:46:20 CST 2020
     */
    public void setWebId(Integer webId) {
        this.webId = webId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column web.web_user_id
     *
     * @return the value of web.web_user_id
     * @mbg.generated Fri Nov 27 19:46:20 CST 2020
     */
    public Integer getWebUserId() {
        return webUserId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column web.web_user_id
     *
     * @param webUserId the value for web.web_user_id
     * @mbg.generated Fri Nov 27 19:46:20 CST 2020
     */
    public void setWebUserId(Integer webUserId) {
        this.webUserId = webUserId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column web.web_is_open
     *
     * @return the value of web.web_is_open
     * @mbg.generated Fri Nov 27 19:46:20 CST 2020
     */
    public Boolean getWebIsOpen() {
        return webIsOpen;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column web.web_is_open
     *
     * @param webIsOpen the value for web.web_is_open
     * @mbg.generated Fri Nov 27 19:46:20 CST 2020
     */
    public void setWebIsOpen(Boolean webIsOpen) {
        this.webIsOpen = webIsOpen;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column web.web_domain
     *
     * @return the value of web.web_domain
     * @mbg.generated Fri Nov 27 19:46:20 CST 2020
     */
    public String getWebDomain() {
        return webDomain;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column web.web_domain
     *
     * @param webDomain the value for web.web_domain
     * @mbg.generated Fri Nov 27 19:46:20 CST 2020
     */
    public void setWebDomain(String webDomain) {
        this.webDomain = webDomain == null ? null : webDomain.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column web.web_name
     *
     * @return the value of web.web_name
     * @mbg.generated Fri Nov 27 19:46:20 CST 2020
     */
    public String getWebName() {
        return webName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column web.web_name
     *
     * @param webName the value for web.web_name
     * @mbg.generated Fri Nov 27 19:46:20 CST 2020
     */
    public void setWebName(String webName) {
        this.webName = webName == null ? null : webName.trim();
    }
}