package com.lzj.domain;

/**todo 文章有关的查询都没有设置分页，下一步就要做
 * 分页条件
 */
public class LimitCondition {
    private Integer first;
    private Integer second;

    public LimitCondition(Integer first, Integer second) {
        this.first = first;
        this.second = second;
    }

    public Integer getFirst() {
        return first;
    }

    public void setFirst(Integer first) {
        this.first = first;
    }

    public Integer getSecond() {
        return second;
    }

    public void setSecond(Integer second) {
        this.second = second;
    }
}
