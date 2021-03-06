package net.sf.rails.common;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import net.sf.rails.game.state.ChangeSet;
import net.sf.rails.util.Util;

import java.util.List;

/**
 * ReportSet contains all messages that reference one ChangeSet
 */
public class ReportSet {

    /**
     * Newline string
     * &#10; is the linefeed character to induce line feed on copy & paste
     */
    private static final String NEWLINE_STRING = "<br>&#10;";

    private final ChangeSet changeSet;


    private final List<String> messages;

    private final String htmlText;
    private final String htmlTextActive;

    public ReportSet(ChangeSet changeSet, List<String> messages) {
        super();

        this.changeSet = changeSet;
        this.messages = messages;

        this.htmlText = toHtml(false);
        this.htmlTextActive = toHtml(true);
    }

    public List<String> getMessages() {
        return messages;
    }

    public String getAsHtml(ChangeSet currentChangeSet) {
        if (currentChangeSet == changeSet) {
            return htmlTextActive;
        } else {
            return htmlText;
        }
    }

    /**
     * converts messages to html string
     *
     * @param activeMessage if true, adds indicator and highlighting for active message
     */
    private String toHtml(boolean activeMessage) {
        if (messages.isEmpty()) {
            if (activeMessage) {
                return ("<span bgcolor=Yellow>" + ReportBuffer.ACTIVE_MESSAGE_INDICATOR + "</span>"
                        + NEWLINE_STRING);
            } else {
                return null;
            }
        }

        StringBuilder s = new StringBuilder();
        boolean init = true;
        for (String message : messages) {
            message = Util.convertToHtml(message);
            if (init) {
                if (activeMessage) {
                    s.append("<span bgcolor=Yellow>" + ReportBuffer.ACTIVE_MESSAGE_INDICATOR);
                }
                s.append("<a href=http://rails:").append(changeSet.getIndex()).append(">");
                s.append(message);
                s.append("</a>");
                if (activeMessage) {
                    s.append("</span>");
                }
                s.append(NEWLINE_STRING);
                init = false;
            } else {
                s.append(message).append(NEWLINE_STRING); // see above
            }
        }
        return s.toString();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .addValue(changeSet)
                .toString();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private ChangeSet changeSet;

        private final List<String> messages = Lists.newArrayList();

        private Builder() {
            // do nothing
        }

        public Builder withChangeSet(ChangeSet changeSet) {
            this.changeSet = changeSet;

            return this;
        }

        public Builder withMessage(String message) {
            this.messages.add(message);

            return this;
        }

        public ReportSet build() {
            return new ReportSet(changeSet, messages);
        }
    }
}
