package com.github.peng49;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class GithubAvatarGenerator {
    //默认外围宽度
    private static final int DEFAULT_AVATAR_FRAME_WIDTH = 35;

    //默认图片宽度
    private static final int DEFAULT_IMAGE_WIDTH = 420;

    //默认背景颜色
    private static final Color DEFAULT_BACKGROUND = new Color(230, 230, 230);

    // Vertex 大小
    private static final int AVATAR_VERTEX_WIDTH = 7;

    /**
     * 头像宽
     */
    private int imageWidth;

    /**
     * 外围宽度
     */
    private int frameWidth;

    /**
     * 背景颜色
     */
    private Color background;

    private int vertexWidth;

    public GithubAvatarGenerator setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
        return this;
    }

    public GithubAvatarGenerator setFrameWidth(int frameWidth) {
        this.frameWidth = frameWidth;
        return this;
    }

    public GithubAvatarGenerator setBackground(Color background) {
        this.background = background;
        return this;
    }

    public GithubAvatarGenerator setVertexWidth(int vertexWidth) {
        if (vertexWidth <= 1 || vertexWidth % 2 == 0) {
            throw new RuntimeException("vertexWidth 必须是一个大于1的奇数");
        }
        this.vertexWidth = vertexWidth;
        return this;
    }

    private int getImageWidth() {
        return this.imageWidth >= 0 ? this.imageWidth : DEFAULT_IMAGE_WIDTH;
    }

    private int getImageHeight() {
        return this.getImageWidth();
    }


    private int getFrameWidth() {
        return this.frameWidth >= 0 ? this.frameWidth : DEFAULT_AVATAR_FRAME_WIDTH;
    }

    private Color getBackground() {
        return background != null ? background : DEFAULT_BACKGROUND;
    }

    private int getAvatarBlockWidth() {
        return (this.getImageWidth() - this.getFrameWidth() * 2) / this.getAvatarVertexWidth();
    }

    private int getAvatarBlockHeight() {
        return this.getAvatarBlockWidth();
    }

    private int getAvatarVertexWidth() {
        return this.vertexWidth > 0 ? this.vertexWidth : AVATAR_VERTEX_WIDTH;
    }

    /**
     * 获取中间列数
     *
     * @return 矩阵的中间列数
     */
    private int getMiddleColumn() {
        return (getAvatarVertexWidth() - 1) / 2;
    }


    /**
     * 获取一个随机填充对称矩阵
     *
     * @return 随机填充对称矩阵
     */
    private boolean[][] getAvatarVertex() {

        int vertexWidth = getAvatarVertexWidth();
        int middleColumn = getMiddleColumn();

        // 新建矩阵
        boolean[][] vertex = new boolean[vertexWidth][vertexWidth];

        // 先随机填充中间一条
        Random random = new Random();
        for (int i = 0; i < vertexWidth; i++) {
            if (random.nextBoolean()) {
                vertex[i][middleColumn] = true;
            }
        }

        // 随机填充半边
        for (int i = 0; i < vertexWidth; i++) {
            for (int j = 0; j < middleColumn; j++) {
                if (random.nextBoolean()) {
                    vertex[i][j] = true;
                }
            }
        }

        // 将填充的半边对称复制到另外半边
        for (int i = 0; i < vertexWidth; i++) {
            for (int j = middleColumn + 1; j < vertexWidth; j++) {
                vertex[i][j] = vertex[i][vertexWidth - 1 - j];
            }
        }

        return vertex;
    }


    /**
     * 获取一个随机头像
     *
     * @return BufferedImage bi
     */
    public BufferedImage getARandomAvatar() {
        BufferedImage bi = new BufferedImage(this.getImageWidth(), this.getImageHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D ig2 = bi.createGraphics();

        Random random = new Random();

        //设置背景颜色
        ig2.setColor(this.getBackground());
        ig2.fillRect(0, 0, this.getImageWidth(), this.getImageHeight());

        boolean[][] vertex = getAvatarVertex();

        Color color = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));

        int vertexWidth = getAvatarVertexWidth();
        int middleColumn = getMiddleColumn();

        //填充中间一列的颜色
        for (int i = 0; i < vertexWidth; i++) {
            if (vertex[i][middleColumn]) {
                ig2.setColor(color);
                ig2.fillRect(
                        this.getFrameWidth() + middleColumn * this.getAvatarBlockWidth(),
                        this.getFrameWidth() + i * this.getAvatarBlockHeight(),
                        this.getAvatarBlockWidth(),
                        this.getAvatarBlockHeight()
                );
            }
        }

        //填充前两列的颜色
        for (int i = 0; i < vertexWidth; i++) {
            for (int j = 0; j < middleColumn; j++) {
                if (vertex[j][i]) {
                    ig2.setColor(color);
                    ig2.fillRect(
                            this.getFrameWidth() + j * this.getAvatarBlockWidth(),
                            this.getFrameWidth() + i * this.getAvatarBlockHeight(),
                            this.getAvatarBlockWidth(),
                            this.getAvatarBlockHeight()
                    );

                    //填充对称位置
                    ig2.fillRect(
                            this.getFrameWidth() + (vertexWidth - 1 - j) * this.getAvatarBlockWidth(),
                            this.getFrameWidth() + i * this.getAvatarBlockHeight(),
                            this.getAvatarBlockWidth(),
                            this.getAvatarBlockHeight()
                    );
                }
            }
        }
        return bi;
    }
}
