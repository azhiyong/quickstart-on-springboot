package xyz.mdou.springboot.web.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.mdou.springboot.web.dto.ExcelDto;
import xyz.mdou.springboot.web.dto.response.RestfulResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("multipart")
public class MultipartController {

    @GetMapping("/generate")
    public void generateExcel(HttpServletResponse response) {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = "template" + ExcelTypeEnum.XLSX.getValue();
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        try {
            EasyExcel.write(response.getOutputStream(), ExcelDto.class)
                    .sheet("WorkSheet")
                    .doWrite(data());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadExcel(
            HttpServletResponse response,
            HttpServletRequest request) {
        String targetFileName = request.getServletContext().getRealPath("/") + "/template.xlsx";
        File targetFile = new File(targetFileName);
        HttpHeaders headers = new HttpHeaders();
        response.setHeader("Content-disposition", "attachment;filename=" + targetFileName);
        try {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(targetFile));
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(targetFile.length())
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(resource);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/upload")
    public RestfulResponse<List<ExcelDto>> uploadExcel(
            @RequestParam("file") MultipartFile file,
            HttpServletRequest request) {
        String targetFileName = request.getServletContext().getRealPath("/") + "/template.xlsx";
        File targetFile = new File(targetFileName);
        try {
            file.transferTo(targetFile);
            List<ExcelDto> data = EasyExcel.read(targetFile)
                    .sheet("WorkSheet")
                    .doReadSync();
            return RestfulResponse.ok(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<ExcelDto> data() {
        return IntStream.range(1, 10000)
                .mapToObj(i -> new ExcelDto()
                        .setId(i)
                        .setDescription("如果你不知道对象的确切类型，RTTI 会告诉你。" +
                                "但是，有一个限制：必须在编译时知道类型，才能使用 RTTI 检测它，" +
                                "并对信息做一些有用的事情。换句话说，编译器必须知道你使用的所有类。\n" +
                                "起初，这看起来并没有那么大的限制，但是假设你引用了一个对不在程序空间中的对象。" +
                                "实际上，该对象的类在编译时甚至对程序都不可用。也许你从磁盘文件或网络连接中获得了大量的字节，" +
                                "并被告知这些字节代表一个类。由于这个类在编译器为你的程序生成代码后很长时间才会出现，你如何使用这样的类？\n" +
                                "在传统编程环境中，这是一个牵强的场景。但是，当我们进入一个更大的编程世界时，" +
                                "会有一些重要的情况发生。第一个是基于组件的编程，你可以在应用程序构建器集成开发环境中使用快速应用程序开发（RAD）构建项目。" +
                                "这是一种通过将表示组件的图标移动到窗体上来创建程序的可视化方法。然后，通过在编程时设置这些组件的一些值来配置这些组件。" +
                                "这种设计时配置要求任何组件都是可实例化的，它公开自己的部分，并且允许读取和修改其属性。" +
                                "此外，处理图形用户界面（GUI）事件的组件必须公开有关适当方法的信息，以便 IDE 可以帮助程序员覆写这些事件处理方法。" +
                                "反射提供了检测可用方法并生成方法名称的机制。\n" +
                                "在运行时发现类信息的另一个令人信服的动机是提供跨网络在远程平台上创建和执行对象的能力。" +
                                "这称为远程方法调用（RMI），它使 Java 程序的对象分布在许多机器上。这种分布有多种原因。" +
                                "如果你想加速一个计算密集型的任务，你可以把它分解成小块放到空闲的机器上。" +
                                "或者你可以将处理特定类型任务的代码（例如，多层次客户机/服务器体系结构中的“业务规则”）放在特定的机器上，" +
                                "这样机器就成为描述这些操作的公共存储库，并且可以很容易地更改它以影响系统中的每个人。" +
                                "分布式计算还支持专门的硬件，这些硬件可能擅长于某个特定的任务——例如矩阵转换——但对于通用编程来说不合适或过于昂贵。\n" +
                                "类 Class 支持反射的概念， java.lang.reflect 库中包含类 Field、Method 和 Constructor（每一个都实现了 Member 接口）。" +
                                "这些类型的对象由 JVM 在运行时创建，以表示未知类中的对应成员。" +
                                "然后，可以使用 Constructor 创建新对象，get() 和 set() 方法读取和修改与 Field 对象关联的字段，" +
                                "invoke() 方法调用与 Method 对象关联的方法。此外，还可以调用便利方法 getFields()、getMethods()、getConstructors() 等，" +
                                "以返回表示字段、方法和构造函数的对象数组。（你可以通过在 JDK 文档中查找类 Class 来了解更多信息。）" +
                                "因此，匿名对象的类信息可以在运行时完全确定，编译时不需要知道任何信息。\n" +
                                "重要的是要意识到反射没有什么魔力。当你使用反射与未知类型的对象交互时，JVM 将查看该对象，" +
                                "并看到它属于特定的类（就像普通的 RTTI）。在对其执行任何操作之前，必须加载 Class 对象。" +
                                "因此，该特定类型的 .class 文件必须在本地计算机上或通过网络对 JVM 仍然可用。" +
                                "因此，RTTI 和反射的真正区别在于，使用 RTTI 时，编译器在编译时会打开并检查 .class 文件。" +
                                "换句话说，你可以用“正常”的方式调用一个对象的所有方法。通过反射，.class 文件在编译时不可用；它由运行时环境打开并检查。")
                        .setCreateTime(new Date()))
                .collect(Collectors.toList());
    }
}
