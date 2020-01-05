package xyz.mdou.springboot.web.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class ExcelDto {

    @ExcelProperty("ID")
    private Integer id;

    @ExcelProperty("description")
    private String description;

    @ExcelProperty("createTime")
    @DateTimeFormat(value = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}
