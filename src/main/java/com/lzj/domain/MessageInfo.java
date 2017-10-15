package com.lzj.domain;

public class MessageInfo extends BaseEntity {
    private String content;
    //发送系统广播消息时，toUserId=-1 代表所有人，fromUserId 表示admin
    private Integer fromUserId;
    private Integer toUserId;
    private Boolean type;
    //1,系统消息
    //2,评论消息
    //3,别人发送的私信息
    private Integer flag;

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Integer fromUserId) {
        this.fromUserId = fromUserId;
    }

    public Integer getToUserId() {
        return toUserId;
    }

    public void setToUserId(Integer toUserId) {
        this.toUserId = toUserId;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageInfo info = (MessageInfo) o;

        if (getContent() != null ? !getContent().equals(info.getContent()) : info.getContent() != null) return false;
        if (getFromUserId() != null ? !getFromUserId().equals(info.getFromUserId()) : info.getFromUserId() != null)
            return false;
        if (getToUserId() != null ? !getToUserId().equals(info.getToUserId()) : info.getToUserId() != null)
            return false;
        if (getType() != null ? !getType().equals(info.getType()) : info.getType() != null) return false;
        return getFlag() != null ? getFlag().equals(info.getFlag()) : info.getFlag() == null;
    }

    @Override
    public int hashCode() {
        int result = getContent() != null ? getContent().hashCode() : 0;
        result = 31 * result + (getFromUserId() != null ? getFromUserId().hashCode() : 0);
        result = 31 * result + (getToUserId() != null ? getToUserId().hashCode() : 0);
        result = 31 * result + (getType() != null ? getType().hashCode() : 0);
        result = 31 * result + (getFlag() != null ? getFlag().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MessageInfo{" +
                "content='" + content + '\'' +
                ", fromUserId=" + fromUserId +
                ", toUserId=" + toUserId +
                ", type=" + type +
                ", flag=" + flag +
                '}';
    }

    public static class   FLAG{
        public static Integer COMMENT_FLAG=2;
        public static Integer SYSTEM_FLAG=1;
        public static Integer PERSON_FLAG=3;
    }
}
