package com.xakcop.search.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;

public abstract class SearchAbstractHandler extends AbstractHandler {

    ITextEditor editor;
    ITextSelection textSelection;
    IDocument doc;

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        if (!initContext(event)) {
            return null;
        }
        try {
            if (textSelection.getLength() == 0) {
                if (isCarretOnWord()) {
                    selectWordUnderCarret();
                }
            }
            doSearch(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    boolean initContext(ExecutionEvent event) throws ExecutionException {
        IEditorPart editorPart = HandlerUtil.getActiveEditorChecked(event);
        if (!(editorPart instanceof ITextEditor)) {
            return false;
        }
        editor = (ITextEditor) editorPart;
        ISelection selection = editor.getSelectionProvider().getSelection();
        if (!(selection instanceof ITextSelection)) {
            return false;
        }
        textSelection = (ITextSelection) selection;
        IDocumentProvider docProvider = editor.getDocumentProvider();
        if (docProvider == null) {
            return false;
        }
        doc = docProvider.getDocument(editor.getEditorInput());
        if (doc == null) {
            return false;
        }
        return true;
    }

    boolean isCarretOnWord() throws BadLocationException {
        int offset = textSelection.getOffset();
        if (!(offset >= 0 && offset < doc.getLength())) {
            return false;
        }
        return Character.isLetterOrDigit(doc.getChar(offset));
    }

    void selectWordUnderCarret() throws BadLocationException {
        int offset = textSelection.getOffset();
        int startOffset = getStartOffset(offset);
        int endOffset = getEndOffset(offset);
        int length = endOffset - startOffset;
        editor.selectAndReveal(startOffset, length);
    }

    int getStartOffset(int offset) throws BadLocationException {
        for (; offset >= 0 ; offset--) {
            char ch = doc.getChar(offset);
            if (!Character.isLetterOrDigit(ch)) {
                break;
            }
        }
        return offset + 1;
    }

    int getEndOffset(int offset) throws BadLocationException {
        for (; offset < doc.getLength() ; offset++) {
            char ch = doc.getChar(offset);
            if (!Character.isLetterOrDigit(ch)) {
                break;
            }
        }
        return offset;
    }

    void doSearch(ExecutionEvent event) throws Exception {
        IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
        ICommandService cmdService = (ICommandService) window.getService(ICommandService.class);
        Command cmd = cmdService.getCommand(getFindCommandId());
        cmd.executeWithChecks(event);
    }

    abstract String getFindCommandId();
}
