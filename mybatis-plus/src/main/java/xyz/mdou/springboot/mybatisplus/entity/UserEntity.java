package xyz.mdou.springboot.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;

@Data
@Accessors(chain = true)
@TableName("user")
public class UserEntity {

    @TableId
    private Long uid;

    private String name;

    private Date birthday;

    @TableField(value = "create_date", jdbcType = JdbcType.TIMESTAMP)
    private Date createDate = new Date();

    @TableField(value = "update_date", update="now()", jdbcType = JdbcType.TIMESTAMP)
    private Date updateDate = new Date();

}
