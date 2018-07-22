package com.hackmidwest.elasticsearch;

import java.net.InetAddress;
import java.util.Map;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.search.SearchHit;

public class ElasticSearch {

	public static void main(String[] args) {
		searchDocument();
	}

	TransportClient transportClient() throws UnknownHostException  {
        return new PreBuiltTransportClient(
            Settings.builder()-
                .put(ClusterName.CLUSTER_NAME_SETTING.getKey(), "es-catalog")
                .build()
            )
            .addTransportAddress(new InetSocketTransportAddress(
                InetAddress.getByName("localhost"), 9300));
    }
}
