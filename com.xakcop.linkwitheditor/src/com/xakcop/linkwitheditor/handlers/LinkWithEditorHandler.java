package com.xakcop.linkwitheditor.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.handlers.HandlerUtil;

public class LinkWithEditorHandler extends AbstractHandler {

    public LinkWithEditorHandler() {
    }

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
        IWorkbenchPage activePage = window.getActivePage();
        IViewPart view = activePage.findView(JavaUI.ID_PACKAGES);
        if (view != null) {
            activePage.activate(view);
            ICommandService cmdService = (ICommandService) window.getService(ICommandService.class);
            Command cmd = cmdService.getCommand(IWorkbenchCommandConstants.NAVIGATE_TOGGLE_LINK_WITH_EDITOR);
            try {
                cmd.executeWithChecks(event);
            } catch (NotDefinedException e) {
                e.printStackTrace();
            } catch (NotEnabledException e) {
                e.printStackTrace();
            } catch (NotHandledException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
