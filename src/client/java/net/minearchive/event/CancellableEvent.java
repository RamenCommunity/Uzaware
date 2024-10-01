package net.minearchive.event;

public class CancellableEvent {
    private boolean cancelled = false;

    public void cancel() {
        setCancelled(true);
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public boolean isCancelled() {
        return cancelled;
    }
}
