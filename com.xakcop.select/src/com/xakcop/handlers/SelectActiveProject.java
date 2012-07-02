package com.xakcop.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.internal.ui.packageview.PackageExplorerPart;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.handlers.HandlerUtil;

public class SelectActiveProject extends AbstractHandler {

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        IEditorPart activeEditor = HandlerUtil.getActiveEditor(event);
        if (activeEditor == null) {
            return null;
        }
        IWorkbenchPage page = activeEditor.getSite().getWorkbenchWindow().getActivePage();
        IViewPart viewPart = page.findView("org.eclipse.jdt.ui.PackageExplorer");
        if (viewPart == null) {
            return null;
        }
        PackageExplorerPart pkgExplorer = (PackageExplorerPart) viewPart;
        IEditorInput editorInput = activeEditor.getEditorInput();
        IResource resource = (IResource) editorInput.getAdapter(IResource.class);
        IProject project = resource.getProject();
        pkgExplorer.tryToReveal(project);
        pkgExplorer.setFocus();
        return null;
    }

}
