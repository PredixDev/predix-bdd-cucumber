Feature: Support to create/update/delete assets and query by assets ID name 
  As an Assets Modeler,
  I am able to create/update/delete assets and query by assets ID name 
  
Scenario: Create/update/delete assets

    Given new assetId "rmd_user_test_asset_sv" with description "new asset - description" _Asset_
    Then the asset with name "rmd_user_test_asset_sv" and description "new asset - description" should be returned _Asset_
    When update the assetId "rmd_user_test_asset_sv" with description "update asset - description" _Asset_
    Then the asset with name "rmd_user_test_asset_sv" and description "update asset - description" should be returned _Asset_
    When a user delete the asset by asset id "rmd_user_test_asset_sv" _Asset_
    Then the asset with asset id "rmd_user_test_asset_sv" not exist _Asset_

   # When a user queries the asset by asset id "compressor-2015" _Asset_
   # Then the asset with name "compressor-2015" should be returned _Asset_ 

  
