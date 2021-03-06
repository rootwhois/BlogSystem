package cn.blog.dao;

import cn.blog.domain.Web;
import cn.blog.domain.WebExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WebMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table web
     *
     * @mbg.generated Fri Nov 27 19:46:20 CST 2020
     */
    long countByExample(WebExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table web
     *
     * @mbg.generated Fri Nov 27 19:46:20 CST 2020
     */
    int deleteByExample(WebExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table web
     *
     * @mbg.generated Fri Nov 27 19:46:20 CST 2020
     */
    int deleteByPrimaryKey(Integer webId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table web
     *
     * @mbg.generated Fri Nov 27 19:46:20 CST 2020
     */
    int insert(Web record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table web
     *
     * @mbg.generated Fri Nov 27 19:46:20 CST 2020
     */
    int insertSelective(Web record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table web
     *
     * @mbg.generated Fri Nov 27 19:46:20 CST 2020
     */
    List<Web> selectByExample(WebExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table web
     *
     * @mbg.generated Fri Nov 27 19:46:20 CST 2020
     */
    Web selectByPrimaryKey(Integer webId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table web
     *
     * @mbg.generated Fri Nov 27 19:46:20 CST 2020
     */
    int updateByExampleSelective(@Param("record") Web record, @Param("example") WebExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table web
     *
     * @mbg.generated Fri Nov 27 19:46:20 CST 2020
     */
    int updateByExample(@Param("record") Web record, @Param("example") WebExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table web
     *
     * @mbg.generated Fri Nov 27 19:46:20 CST 2020
     */
    int updateByPrimaryKeySelective(Web record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table web
     *
     * @mbg.generated Fri Nov 27 19:46:20 CST 2020
     */
    int updateByPrimaryKey(Web record);
}