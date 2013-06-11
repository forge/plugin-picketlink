package org.picketlink.forge;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.forge.project.Project;
import org.jboss.forge.project.facets.DependencyFacet;
import org.jboss.forge.test.AbstractShellTest;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;

public class PicketLinkPluginTest extends AbstractShellTest
{
   @Deployment
   public static JavaArchive getDeployment()
   {
      return AbstractShellTest.getDeployment()
               .addPackages(true, PicketLinkPlugin.class.getPackage());
   }

   @Test
   public void testSetup() throws Exception
   {
      Project project = initializeJavaProject();
      queueInputLines("");
      Assert.assertFalse(project.hasFacet(PicketLinkFacet.class));
      getShell().execute("picketlink setup");
      Assert.assertTrue(project.hasFacet(PicketLinkFacet.class));
      DependencyFacet facet = project.getFacet(DependencyFacet.class);
      Assert.assertTrue(facet.hasEffectiveDependency(PicketLinkFacet.API_DEPENDENCY));
      Assert.assertTrue(facet.hasEffectiveDependency(PicketLinkFacet.IMPL_DEPENDENCY));
   }
}
