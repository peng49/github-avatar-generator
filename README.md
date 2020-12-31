## Github 头像生成器

### 使用
1. 添加依赖
```
        <dependency>
            <groupId>com.github.peng49</groupId>
            <artifactId>github-avatar-generator</artifactId>
            <version>0.1</version>
        </dependency>
```

2. 代码
```java
    import com.github.peng49.GithubAvatarGenerator;
    
    import javax.imageio.ImageIO;
    import java.awt.image.BufferedImage;
    import java.io.File;
    import java.io.IOException;
    import java.util.UUID;
    
    public class Test {
        public static void main(String[] args) throws IOException {    
            //创建一个生成器对象
            GithubAvatarGenerator avatarGenerator = new GithubAvatarGenerator();
            
            //获取一个BufferedImage对象
            BufferedImage avatar = avatarGenerator.getARandomAvatar();
        
            //通过 BufferedImage 对象生成一个PNG文件            
            String filename = UUID.randomUUID() + ".png";
            File file = new File("./" + filename);
            ImageIO.write(avatar, "PNG", file);

            System.out.println(filename);
        }
    }
```