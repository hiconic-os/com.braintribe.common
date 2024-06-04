package com.braintribe.common;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.function.Consumer;

import com.braintribe.logging.Logger;

public class WeakClosing implements AutoCloseable {
	private Logger logger = Logger.getLogger(WeakClosing.class);
	private ReferenceQueue<Object> queue = new ReferenceQueue<Object>();
	private Thread thread;
	
	public WeakClosing() {
		thread = new Thread(this::doWork);
		thread.start();
	}
	
	public <T> void closeWhenWeak(T closeable, Consumer<T> closer) {
		new ClosingReference<T>(closeable, closer);
	}
	
	public void closeWhenWeak(AutoCloseable closeable) {
		closeWhenWeak(closeable, c -> {
			try {
				c.close();
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
	}
	
	@Override
	public void close() {
		thread.interrupt();
		try {
			thread.join();
		}
		catch (InterruptedException e) {
			logger.error("interrupted when shutting down worker thread", e);
		}
	}
	
	private void doWork() {
		try {
			while (true) {
				ClosingReference<?> ref = (ClosingReference<?>) queue.remove();
				try {
					ref.close();
				} catch (Exception e) {
					logger.error("error while closing weakened object", e);
				}
			}
		}
		catch (InterruptedException e) {
			// ignored to end work after stopping
		}
	}
	
	private class ClosingReference<T> extends WeakReference<T> {
		private Consumer<T> closer;
		
		public ClosingReference(T referent, Consumer<T> closer) {
			super(referent, queue);
			this.closer = closer;
		}
		
		public void close() {
			closer.accept(this.get());
		}
	}
}
