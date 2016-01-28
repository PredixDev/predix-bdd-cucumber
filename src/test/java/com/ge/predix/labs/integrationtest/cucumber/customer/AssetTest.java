package com.ge.predix.labs.integrationtest.cucumber.customer;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.codehaus.jettison.json.JSONArray;
import org.springframework.util.Assert;

import com.ge.ams.dto.Asset;
import com.ge.predix.labs.common.JsonMapper;
import com.ge.predix.labs.integrationtest.RestTestBase;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class AssetTest extends RestTestBase {

	private String response;
	
	@Given("^new assetId \"([^\"]*)\" with description \"([^\"]*)\" _Asset_$")
	public void new_assetId_with_description__Asset_(String assetId, String description) throws Throwable {
		Asset asset = new Asset();
		asset.setAssetId(assetId);
		String uri = "/asset/" + assetId;
		asset.setUri(uri);
		asset.setDescription(description);
        List<Asset> list = new ArrayList<>();
        list.add(asset);
		rest.post(config.paths.prefix + "/asset", JsonMapper.toJson(list));
	}
	
	@When("^update the assetId \"([^\"]*)\" with description \"([^\"]*)\" _Asset_$")
	public void update_the_assetId_with_description__Asset_(String assetId, String description) throws Throwable {
		String url =  "/asset/" + assetId;
		Asset asset  = retrieveOne(Asset.class, url);
		asset.setDescription(description);
        List<Asset> list = new ArrayList<>();
        list.add(asset);
		rest.post(config.paths.prefix + "/asset", JsonMapper.toJson(list));
	}

	@Then("^the asset with name \"([^\"]*)\" and description \"([^\"]*)\" should be returned _Asset_$")
	public void the_asset_with_name_and_description_should_be_returned__Asset_(String assetId, String description) throws Throwable {
		String url =  "/asset/" + assetId;
		Asset asset  = retrieveOne(Asset.class, url);
		Assert.isTrue(assetId.equals(asset.getAssetId()));
		Assert.isTrue(description.equals(asset.getDescription()));
	}
	
	@Then("^the asset with asset id \"([^\"]*)\" not exist _Asset_$")
	public void the_asset_with_asset_id_not_exist__Asset_(String assetId) throws Throwable {
		String url =  "/asset/" + assetId;
		Asset asset;
		try {
		    asset  = retrieveOne(Asset.class, url);
		} catch(Exception e) {
			asset = null;
		}
		Assert.isNull(asset);
	}

	@When("^a user delete the asset by asset id \"([^\"]*)\" _Asset_$")
	public void a_user_delete_the_asset_by_asset_id__Asset_(String assetId) throws Throwable {
		String url = "/asset/" + assetId;
		delete(url);
	}

	@When("^a user queries the asset by asset id \"([^\"]*)\" _Asset_$")
	public void a_user_queries_the_asset_by_asset_id__Asset_(String name) throws Throwable {
		String url = config.paths.prefix + "/asset/" + name;
		response = get(url);
	}

	@Then("^the asset with name \"([^\"]*)\" should be returned _Asset_$")
	public void the_asset_with_name_should_be_returned__Asset_(String name) throws Throwable {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = (ObjectNode) mapper.readTree(new JSONArray(response).getString(0));
		Assert.isTrue(node.get("assetId").asText().equals(name));
	}
}
