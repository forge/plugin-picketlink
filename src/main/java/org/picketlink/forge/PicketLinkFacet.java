/*
 * Copyright 2013 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.picketlink.forge;

import javax.inject.Inject;

import org.jboss.forge.project.dependencies.Dependency;
import org.jboss.forge.project.dependencies.DependencyBuilder;
import org.jboss.forge.project.dependencies.DependencyInstaller;
import org.jboss.forge.project.dependencies.DependencyResolver;
import org.jboss.forge.project.dependencies.ScopeType;
import org.jboss.forge.project.facets.BaseFacet;
import org.jboss.forge.project.facets.DependencyFacet;
import org.jboss.forge.shell.Shell;
import org.jboss.forge.shell.plugins.Alias;
import org.jboss.forge.shell.plugins.RequiresFacet;

@Alias("org.picketlink.facet")
@RequiresFacet({ DependencyFacet.class })
public class PicketLinkFacet extends BaseFacet
{
   public static final Dependency API_DEPENDENCY = DependencyBuilder.create().setGroupId("org.picketlink")
            .setArtifactId("picketlink-api").setScopeType(ScopeType.COMPILE);

   public static final Dependency IMPL_DEPENDENCY = DependencyBuilder.create().setGroupId("org.picketlink")
            .setArtifactId("picketlink-impl").setScopeType(ScopeType.RUNTIME);

   @Inject
   private DependencyInstaller installer;

   @Inject
   private DependencyResolver resolver;

   @Inject
   private Shell shell;

   @Override
   public boolean install()
   {
      DependencyFacet facet = getProject().getFacet(DependencyFacet.class);
      if (!project.hasFacet(PicketLinkFacet.class))
      {
         Dependency choice = installer.install(getProject(), API_DEPENDENCY);
         if (!facet.hasEffectiveDependency(IMPL_DEPENDENCY))
         {
            Dependency implVersioned = DependencyBuilder.create(IMPL_DEPENDENCY).setVersion(choice.getVersion());
            installer.install(getProject(), implVersioned);
         }
      }
      return true;
   }

   @Override
   public boolean isInstalled()
   {
      DependencyFacet facet = getProject().getFacet(DependencyFacet.class);
      return facet.hasEffectiveDependency(API_DEPENDENCY);
   }
}
