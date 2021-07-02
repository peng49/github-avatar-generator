package io.github.peng49;

import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class GithubAvatarGeneratorTest {

    @Test
    public void getAvatarBufferedImageTest() throws IOException {
        GithubAvatarGenerator avatarGenerator = new GithubAvatarGenerator();

        for (int i = 0; i < 20; i++) {
            BufferedImage avatar = avatarGenerator.getAvatarBufferedImage();
//            String filename = UUID.randomUUID() + ".png";
//            File file = new File("./" + filename);
//            ImageIO.write(avatar, "PNG", file);

//            System.out.println(filename);
        }
    }
}
