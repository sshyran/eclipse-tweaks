package com.xakcop.search.handlers;

import org.eclipse.ui.texteditor.IWorkbenchActionDefinitionIds;

public class SearchNextHandler extends SearchAbstractHandler {

    @Override
    String getFindCommandId() {
        return IWorkbenchActionDefinitionIds.FIND_NEXT;
    }

}
