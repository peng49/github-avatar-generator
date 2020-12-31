package com.github.peng49;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class GithubAvatarGenerator {
    /**
     * 头像长宽
     */
    private int length = 420;

    /**
     * 背景颜色
     */
    private Color background = new Color(230, 230, 230);

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Color getBackground() {
        return background;
    }

    public void setBackground(Color background) {
        this.background = background;
    }


    // 选出一些大概会比较好看的颜色池用于生成
    private static final int[][] COLOR_POOL_RGB = new int[][]{
            {170, 205, 102},
            {159, 255, 84},
            {209, 206, 0},
            {255, 255, 0},
            {47, 107, 85},
            {47, 255, 173},
            {0, 173, 205},
            {8, 101, 139},
            {180, 180, 238},
            {106, 106, 255},
            {155, 211, 255},
            {204, 50, 153},
            {101, 119, 139}
    };
    // 外围宽度
    private static final int AVATAR_FRAME_WIDTH = 35;


    // Vertex 大小
    private static final int AVATAR_VERTEX_WIDTH = 7;


    private int getAvatarBlockWidth() {
        return (this.getLength() - AVATAR_FRAME_WIDTH * 2) / this.getAvatarVertexWidth();
    }

    private int getAvatarBlockHeight() {
        return this.getAvatarBlockWidth();
    }

    private int getAvatarVertexWidth() {
        return AVATAR_VERTEX_WIDTH;
    }

    /**
     * 中间列数
     * @return
     */
    private int getMiddleColumn(){
        return (getAvatarVertexWidth() - 1) / 2;
    }


    /**
     * 获取一个 5x5 的随机填充对称矩阵
     *
     * @return 5x5 随机填充对称矩阵
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
        BufferedImage bi = new BufferedImage(this.getLength(), this.getLength(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D ig2 = bi.createGraphics();

        Random random = new Random();

        //设置背景颜色
        ig2.setColor(this.getBackground());
        ig2.fillRect(0, 0, this.getLength(), this.getLength());

        boolean[][] vertex = getAvatarVertex();

        Color color = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));

        int vertexWidth = getAvatarVertexWidth();
        int middleColumn = getMiddleColumn();

        //填充中间一列的颜色
        for (int i = 0; i < vertexWidth; i++) {
            System.out.println(AVATAR_FRAME_WIDTH + middleColumn * this.getAvatarBlockWidth());
            if (vertex[i][middleColumn]) {
                ig2.setColor(color);
                ig2.fillRect(
                        AVATAR_FRAME_WIDTH + middleColumn * this.getAvatarBlockWidth(),
                        AVATAR_FRAME_WIDTH + i * this.getAvatarBlockHeight(),
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
                            AVATAR_FRAME_WIDTH + j * this.getAvatarBlockWidth(),
                            AVATAR_FRAME_WIDTH + i * this.getAvatarBlockHeight(),
                            this.getAvatarBlockWidth(),
                            this.getAvatarBlockHeight()
                    );

                    //填充对称位置
                    ig2.fillRect(
                            AVATAR_FRAME_WIDTH + (vertexWidth - 1 - j) * this.getAvatarBlockWidth(),
                            AVATAR_FRAME_WIDTH + i * this.getAvatarBlockHeight(),
                            this.getAvatarBlockWidth(),
                            this.getAvatarBlockHeight()
                    );
                }
            }
        }
        return bi;
    }
}
