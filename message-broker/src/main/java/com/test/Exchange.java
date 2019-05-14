package com.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Exchange {
	// ds
	String name;
	Producer p; // one producer
	Map<String, List<MyQueue>> qs = new HashMap<>(); // routing queue and list
														// of queues(each having
														// one consumer)

	Exchange(String n) {
		name = n;
	}

	void bindProducer(Producer p) {
		this.p = p;
	}

	void bindConsumer(MyQueue q) {
		if (qs.get(q.routingKey) == null)
			qs.put(q.routingKey, new ArrayList<>());
		qs.get(q.routingKey).add(q);
	}

	void addAndRoute(Object payload, String routingKeys) {
		String[] rKeys = routingKeys.split(",");
		for (String key : rKeys) {
			List<MyQueue> Q = qs.get(key);
			for (int i = 0; i < Q.size(); i++) {
				Q.get(i).q.add(payload);
			}
		}
	}
}