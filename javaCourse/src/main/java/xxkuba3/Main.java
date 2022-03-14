package xxkuba3;

import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.plus.webapp.EnvConfiguration;
import org.eclipse.jetty.plus.webapp.PlusConfiguration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.eclipse.jetty.util.component.LifeCycle;
import org.eclipse.jetty.webapp.*;

public class Main {

    public static void main(String[] args) throws Exception {

        var webapp = new WebAppContext();
        webapp.setResourceBase("src/main/webapp");
        webapp.setContextPath("/");
        webapp.setConfigurations(new Configuration[]
                {
                        new WebAppConfiguration(),
                        new AnnotationConfiguration(),
                        new WebInfConfiguration(),
                        new WebXmlConfiguration(),
                        new MetaInfConfiguration(),
                        new FragmentConfiguration(),
                        new EnvConfiguration(),
                        new PlusConfiguration(),
                        new JettyWebXmlConfiguration()
                });
        webapp.setAttribute("org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern", ".*/classes/.*");
        webapp.setAttribute("org.eclipse.jetty.servlet.Default.useFileMappedBuffer", "false");
        var server = new Server(8080);
        server.setHandler(webapp);

        server.addEventListener(new AbstractLifeCycle.AbstractLifeCycleListener(){
            @Override
            public void lifeCycleStopped(LifeCycle event){
               HibernateUtil.close();
            }
        });
        server.start();
        server.join();
    }
}
