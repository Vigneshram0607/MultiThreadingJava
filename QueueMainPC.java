import java.util.*;
class Queue
{
int[] data;
int front,rear;
	int size;
	boolean isEmpty;
	boolean isFull;
public Queue(int size)
	{
		this.size=size;
		this.data = new int[size];
		front=rear=-1;
		isEmpty=true;
		isFull=false;
	}

synchronized int get()
{
if(isEmpty)
		{
			try
			{
				wait();
			}
			catch(Exception e)	{
				System.out.println("UnderFlow\nProgram Terminated");
			System.out.println(e);
			}
		}
		
		
		System.out.println("\nConsumer consumed- " + data[front]);
		
		if(front==rear)
		{
			isEmpty=true;
			isFull=false;
			front=rear=-1;
		}
		else
		{
				front++;
		}
		notify();
		return 1;
}
synchronized void put(int y)
{
while(isFull)
		{
			try
			{
				wait();
			}
			catch(Exception e)	{
				System.out.println("OverFlow\nProgram Terminated");
			System.out.println(e);
			}
		}
		rear++;
		data[rear] = y;
		if(rear==size-1)
		{
			isFull=true;
			isEmpty=false;
		}
		
		System.out.println("\nProducer produced- " + y);
		if(front==-1)
			front++;
		
		notify();
	}
	
}
class Producer implements Runnable
{
Queue q;
Thread t;
Producer(Queue q)
{
this.q = q;

t = new Thread(this, "Producer");

}
public void run()
{

	int i=1;
		while(true)
		{
			q.put(i++);
			try	{	Thread.sleep(300);	}
				catch(Exception e)
		{
			System.out.println("producer Thread Interrupted");
		}
		}


}
public void start()
	{
		System.out.println(t.getName()+" is Started...");
		t.start();
	}

}
class Consumer implements Runnable
{
Queue q;
Thread t;
Consumer(Queue q)
{
this.q = q;

t = new Thread(this, "Consumer");

}
public void run()
{
while(true)
{
q.get();
try	{	Thread.sleep(1000);	}
			catch(Exception e)
		{
			System.out.println("consumer Thread Interrupted");
		}
}
}
public void start()
	{
		System.out.println(t.getName()+" is Started...");
		t.start();
	}
}
class QueueMainPC
{
public static void main(String args[])
{
Queue q = new Queue(2);
Producer prodT = new Producer(q);
Consumer consT = new Consumer(q);
prodT.start();
		consT.start();
		try
		{
			prodT.t.join();
			consT.t.join();
		}
		catch(Exception e)
		{
			System.out.println("main block Interrupted");
		}
		System.out.println("Main Exited....");
}
}
