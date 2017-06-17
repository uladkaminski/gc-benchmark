package com.epam.visual;

public class NestingDoll {
    private String name;
    private NestingDoll nestingDoll;
    private String[] str;

    public NestingDoll(String name) {
        this.name = name;
        str = new String[Integer.MAX_VALUE/100];
    }

    public void putNestingDoll(NestingDoll nestingDoll) {
        if (this.nestingDoll == null){
            this.nestingDoll = nestingDoll;
        } else {
            this.nestingDoll.putNestingDoll(nestingDoll);
        }
    }

    public long getDeep(){
        long deep = 0L;
        NestingDoll temp = this.nestingDoll;
        while (temp != null){
            deep++;
            temp = temp.nestingDoll;
        }
        return deep;
    }

    @Override
    public String toString() {
        return "NestingDoll{" +
                "name='" + name + '\'' +
                ", nestingDoll=" + nestingDoll +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NestingDoll that = (NestingDoll) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return nestingDoll != null ? nestingDoll.equals(that.nestingDoll) : that.nestingDoll == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (nestingDoll != null ? nestingDoll.hashCode() : 0);
        return result;
    }
}
