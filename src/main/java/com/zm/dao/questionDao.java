package com.zm.dao;

import com.zm.model.Question;
import com.zm.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by Ellen on 2017/5/19.
 */
@Mapper
public interface questionDao {
    String TABLE_NAME = " question ";
    String INSERT_FIELDS = " title,content,user_id,comment_count,create_date ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into", TABLE_NAME, " (", INSERT_FIELDS,
            ") values (#{title},#{content},#{userId},#{commentCount},#{createDate})"})
    int addQuestion(Question question);

    List<Question> findLatestQuestion(@Param("userId") int userId,
                                      @Param("offset") int offset,
                                      @Param("limit") int limit);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    Question findQuestionById(int id);

    @Update({"update ",TABLE_NAME,"set comment_count = #{commentCount} where id = #{id}"})
    Question updateQuestion(Question question);
}
