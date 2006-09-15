package org.shiftone.jrat.inject.ant;



import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;

import org.shiftone.jrat.core.criteria.MatcherMethodCriteria;
import org.shiftone.jrat.core.criteria.IncludeExcludeMethodCriteria;
import org.shiftone.jrat.inject.Injector;

import java.io.File;


/**
 * @author $Author: jeffdrost $
 * @version $Revision: 1.23 $
 */
public class InjectTask extends AbstractFileTask {

    private Injector                     injector;
    private IncludeExcludeMethodCriteria methodCriteria;

    public InjectTask() {

        injector       = new Injector();
        methodCriteria = new IncludeExcludeMethodCriteria();

        injector.setMethodCriteria(methodCriteria);
    }


    public MatcherMethodCriteria createInclude() {

        MatcherMethodCriteria newCriteria = new MatcherMethodCriteria();

        methodCriteria.addPositive(newCriteria);

        return newCriteria;
    }


    public MatcherMethodCriteria createExclude() {

        MatcherMethodCriteria newCriteria = new MatcherMethodCriteria();

        methodCriteria.addNegative(newCriteria);

        return newCriteria;
    }


    protected void processFile(File file) throws BuildException {

        log("processFile(" + file.getAbsolutePath() + ")", Project.MSG_VERBOSE);

        try
        {
            injector.inject(file);
        }
        catch (Exception e)
        {
            throw new BuildException(e);
        }
    }


    protected void validateFile(File file) throws BuildException {

        if (file.exists() == false)
        {
            throw new BuildException("File does not exist : " + file.getAbsolutePath());
        }
    }
}
