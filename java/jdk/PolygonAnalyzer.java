package com.gaoge.code;

/**
 * @author gaoge
 * @date 2018/11/10 10:46
 */
public class PolygonAnalyzer {
    /**
     * 判断一个点 P 是否在某个坐标集合 P_set 围成的封闭平面 Polygon 内
     * 首先：先探究问题的关键。点与封闭几何可能的关系
     *   1. 区域外
     *   2. 包含
     *   3. 边界点
     *
     *   区域外：对于第一种情况，我们从 P 发出的任一条射线与 Polygon 有且仅有三种可能关系
     *       1. 与 Polygon 相交：此时，P与Polygon的所有交点必然是偶数个的
     *       2. 与 Polygon '相切'(经过其一个点或者一条边)：
     *       3. 与 Polygon 相离：没有交集
     *   边界点：很好判断，相邻两点取一点与P连，比较边界和新鲜段斜率即可
     *
     *   包含：包含的关系中，最明显的特点便是：从 P 发出得任一条射线必然与之相交，且交点个数必然为奇数个（与射线同线边除外），
     *       那么求 P 与 Polygon 之间是否存在包含关系则可以转化为一个简单的数学问题：
     *       由于任意射线均满足该条件，那么我们可以把问题简化，有 P 引一条与 X 轴或 Y 轴平行的射线进行分析，判断能产生的交点个数即可
     *       如下Polygon的contains实现
     */
    private int npoints;
    private int[] xpoints;
    private int[] ypoints;
    public boolean contains(double x, double y) {
        int hits = 0;
        // 前一个坐标
        int lastx = xpoints[npoints - 1];
        int lasty = ypoints[npoints - 1];
        int curx, cury;

        // Walk the edges of the polygon
        for (int i = 0; i < npoints; lastx = curx, lasty = cury, i++) {

            // 当前坐标
            curx = xpoints[i];
            cury = ypoints[i];

            if (cury == lasty) {
                continue;
            }

            int leftx;
            // 只取当前点沿 Y 轴正向的边做相交检测
            // 即只与当前点 P 右边（XY坐标系中）的边做检测

            // 判断当前边是否有点在 P 右侧，有则继续，否则检测下一个边
            if (curx < lastx) {
                if (x >= lastx) {
                    continue;
                }
                leftx = curx;
            } else {
                if (x >= curx) {
                    continue;
                }
                leftx = lastx;
            }

            double test1, test2;
            // 检测当前边是否在 P 右侧且 P.y 在当前边的Y坐标范围内（即判断是否有相交可能），是则继续，否则检测下一条边
            if (cury < lasty) {
                if (y < cury || y >= lasty) {
                    continue;
                }
                // P.x 一定在当前边右侧，焦点++，继续检测下一条边
                if (x < leftx) {
                    hits++;
                    continue;
                }
                test1 = x - curx;
                test2 = y - cury;
            } else {
                if (y < lasty || y >= cury) {
                    continue;
                }
                // P.x 一定在当前边右侧，焦点++，继续检测下一条边
                if (x < leftx) {
                    hits++;
                    continue;
                }
                test1 = x - lastx;
                test2 = y - lasty;
            }

            //通过斜率判断当前边是否在P的右侧，是则焦点数++
            if (test1 < (test2 / (lasty - cury) * (lastx - curx))) {
                hits++;
            }
        }

        //奇偶判断用位操作，流啤
        return ((hits & 1) != 0);
    }
}

