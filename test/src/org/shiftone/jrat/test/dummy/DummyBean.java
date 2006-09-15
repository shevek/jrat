package org.shiftone.jrat.test.dummy;



import org.shiftone.jrat.util.log.Logger;


/**
 * Class DummyBean
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.2 $
 */
public class DummyBean {

    private static final Logger LOG   = Logger.getLogger(DummyBean.class);
    private String           one   = null;
    private String           two   = null;
    private Integer          three = null;
    private int              four  = 1;
    private Long             five  = null;
    private long             six   = 1;
    private Boolean          seven = null;
    private boolean          eight = false;

    /**
     * Method getEight
     *
     * @return .
     */
    public boolean getEight() {
        return eight;
    }


    /**
     * Method setEight
     *
     * @param eight .
     */
    public void setEight(boolean eight) {
        this.eight = eight;
    }


    /**
     * Method getFive
     *
     * @return .
     */
    public Long getFive() {
        return five;
    }


    /**
     * Method setFive
     *
     * @param five .
     */
    public void setFive(Long five) {
        this.five = five;
    }


    /**
     * Method getFour
     *
     * @return .
     */
    public int getFour() {
        return four;
    }


    /**
     * Method setFour
     *
     * @param four .
     */
    public void setFour(int four) {
        this.four = four;
    }


    /**
     * Method getOne
     *
     * @return .
     */
    public String getOne() {
        return one;
    }


    /**
     * Method setOne
     *
     * @param one .
     */
    public void setOne(String one) {
        this.one = one;
    }


    /**
     * Method getSeven
     *
     * @return .
     */
    public Boolean getSeven() {
        return seven;
    }


    /**
     * Method setSeven
     *
     * @param seven .
     */
    public void setSeven(Boolean seven) {
        this.seven = seven;
    }


    /**
     * Method getSix
     *
     * @return .
     */
    public long getSix() {
        return six;
    }


    /**
     * Method setSix
     *
     * @param six .
     */
    public void setSix(long six) {
        this.six = six;
    }


    /**
     * Method getThree
     *
     * @return .
     */
    public Integer getThree() {
        return three;
    }


    /**
     * Method setThree
     *
     * @param three .
     */
    public void setThree(Integer three) {
        this.three = three;
    }


    /**
     * Method getTwo
     *
     * @return .
     */
    public String getTwo() {
        return two;
    }


    /**
     * Method setTwo
     *
     * @param two .
     */
    public void setTwo(String two) {
        this.two = two;
    }
}
