package com.group1.ecocredit.QuartzTest;

import com.group1.ecocredit.QuartzTest.QuartzTestJob;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class QuartzTest {

    @Mock
    private JobExecutionContext jobExecutionContext;

    @InjectMocks
    private QuartzTestJob quartzTestJob;

    @Test
    public void testExecute() throws JobExecutionException {
        // Call the execute method of the job
        quartzTestJob.execute(jobExecutionContext);

        verify(jobExecutionContext).getJobDetail();
    }
}
