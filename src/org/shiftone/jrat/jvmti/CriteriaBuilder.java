package org.shiftone.jrat.jvmti;



import org.shiftone.jrat.core.Settings;
import org.shiftone.jrat.core.criteria.AndMethodCriteria;
import org.shiftone.jrat.core.criteria.ClassMatcherMethodCriteria;
import org.shiftone.jrat.core.criteria.MethodCriteria;
import org.shiftone.jrat.core.criteria.NotMethodCriteria;
import org.shiftone.jrat.util.log.Logger;
import org.shiftone.jrat.util.regex.CompositeMatcher;
import org.shiftone.jrat.util.regex.Matcher;


/**
 * @author Jeff Drost
 * @version $Revision: 1.6 $
 */
public class CriteriaBuilder {

    private static final Logger LOG              = Logger.getLogger(CriteriaBuilder.class);
    public static String[]      DEFAULT_EXCLUDES =
    {
        "bsh.*",           //
        "com.sun.*",       //
        "EDU.oswego.*",    //
        "gnu.*",           //
        "org.apache.*",    //
        "org.dom4j.*",     //
        "org.hsqldb.*",    //
        "org.jboss.*",     //
        "org.jnp.*",       //
        "$Proxy*"          //
    };

    public MethodCriteria build(String agentArg) {

        AndMethodCriteria and = new AndMethodCriteria();

        if (agentArg != null)
        {
            int xindex = agentArg.indexOf("!");

            if (xindex != -1)
            {

                // this is a "!" so there is an exclude
                String includeString = agentArg.substring(0, xindex);
                String excludeString = agentArg.substring(xindex + 1);

                if (includeString.length() > 0)
                {

                    // there is also an include
                    Matcher matcher = CompositeMatcher.buildCompositeGlobMatcher(includeString);

                    and.addCriteria(new ClassMatcherMethodCriteria(matcher));
                }

                Matcher matcher = CompositeMatcher.buildCompositeGlobMatcher(excludeString);

                and.addCriteria(new NotMethodCriteria(new ClassMatcherMethodCriteria(matcher)));
            }
            else
            {

                // there is *only* an include
                Matcher matcher = CompositeMatcher.buildCompositeGlobMatcher(agentArg);

                and.addCriteria(new ClassMatcherMethodCriteria(matcher));
            }
        }

        if (Settings.isInjectorDefaultExcludesEnabled())
        {
            Matcher matcher = CompositeMatcher.buildCompositeGlobMatcher(DEFAULT_EXCLUDES);

            and.addCriteria(new NotMethodCriteria(new ClassMatcherMethodCriteria(matcher)));
        }

        return and;
    }


    private static void dump(String text) {

        CriteriaBuilder builder  = new CriteriaBuilder();
        MethodCriteria  criteria = builder.build(text);

        LOG.info(text + " => " + criteria);
    }


    public static void main(String[] args) {

        dump("this.that.*");
        dump("!this.that.*");
        dump("edu.psu.*!edu.mit.*");
    }
}
