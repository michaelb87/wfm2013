package wfm.tx;

import java.io.Serializable;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.transaction.Status;
import javax.transaction.UserTransaction;

@Interceptor
@Transactional
public class TransactionInterceptor implements Serializable {

  private static final long serialVersionUID = 1L;

  @Inject
  private UserTransaction userTransaction;

  @AroundInvoke
  public Object invoke(InvocationContext ctx) throws Throwable {
    boolean startNew = userTransaction.getStatus() == Status.STATUS_NO_TRANSACTION;

    if (startNew) {
      userTransaction.begin();
    }
    try {
      Object result = ctx.proceed();
      if (startNew) {
        userTransaction.commit();
      }
      return result;
    } catch (Exception e) {
      if (startNew) {
        userTransaction.rollback();
      }
      throw e;
    }
  }
}