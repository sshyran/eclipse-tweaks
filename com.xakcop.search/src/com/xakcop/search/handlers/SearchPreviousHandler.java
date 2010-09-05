package com.xakcop.search.handlers;

import org.eclipse.ui.texteditor.IWorkbenchActionDefinitionIds;

public class SearchPreviousHandler extends SearchAbstractHandler {

    @Override
    String getFindCommandId() {
        return IWorkbenchActionDefinitionIds.FIND_PREVIOUS;
    }

}
