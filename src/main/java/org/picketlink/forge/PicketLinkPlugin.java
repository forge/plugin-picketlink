package org.picketlink.forge;

import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.jboss.forge.maven.MavenCoreFacet;
import org.jboss.forge.project.Project;
import org.jboss.forge.project.dependencies.DependencyInstaller;
import org.jboss.forge.project.facets.events.InstallFacets;
import org.jboss.forge.shell.plugins.Alias;
import org.jboss.forge.shell.plugins.PipeOut;
import org.jboss.forge.shell.plugins.Plugin;
import org.jboss.forge.shell.plugins.RequiresFacet;
import org.jboss.forge.shell.plugins.RequiresProject;
import org.jboss.forge.shell.plugins.SetupCommand;

/**
 * Picketlink Plugin
 */
@Alias("picketlink")
@RequiresProject
@RequiresFacet(MavenCoreFacet.class)
public class PicketLinkPlugin implements Plugin
{
   @Inject
   private Project project;

   @Inject
   private Event<InstallFacets> event;

   @Inject
   private DependencyInstaller installer;

   @SetupCommand
   public void setupPicketlink(PipeOut out)
   {
      event.fire(new InstallFacets(PicketLinkFacet.class));
      if (project.hasFacet(PicketLinkFacet.class))
      {
         out.println("PicketLink is installed.");
      }
   }
}
