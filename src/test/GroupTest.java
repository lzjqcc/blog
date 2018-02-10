import com.lzj.Application;
import com.lzj.dao.dto.GroupDto;
import com.lzj.service.impl.GroupService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class GroupTest {
    @Autowired
    GroupService groupService;
    @Autowired
    PlatformTransactionManager manager;
    @Test
    public void tese() {
       // TransactionDefinition definition = new DefaultTransactionDefinition();
        //TransactionStatus status = manager.getTransaction(definition);
        GroupDto dto = new GroupDto();
        dto.setGroupName("小孩子1");
        dto.setCurrentAccountId(1);
        groupService.insertGroup(dto);
        //manager.commit(status);
    }
}
