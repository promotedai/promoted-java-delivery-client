# promoted-java-delivery-client

A Java Client to contact the Promoted.ai Delivery API.

The promoted-java-delivery-client library attempts to minimize dependencies to ease integration into large projects. At runtime, it requires:
* `jackson` version 2.3.1
* Java 11

## Features

- Demonstrates and implements the recommended practices and data types for calling Promoted.ai's Delivery API.
- Client-side position assignment and paging when not using results from Delivery API, for example when logging only or as part of an experiment control.

## Full example

[promotedai/promoted-java-delivery-client-example GitHub Repository](https://github.com/promotedai/promoted-java-delivery-client-example).

## Creating a PromotedDelieryClient

We recommend creating a `PromotedDeliveryClient` in a separate file so it can be reused.
It is thread-safe and intended to be used as a singleton, leveraging `java.net.http.HttpClient` for calling Promoted.ai's services.

### `PromotedClient.java`

```java
PromotedDeliveryClient client = PromotedDeliveryClient.builder()
    .withExecutor(Executors.newFixedThreadPool(10))
    .withMetricsExecutor(ExecutorService.newFixedThreadPool(10))
    .withDeliveryEndpoint("<get this from Promoted.ai>")
    .withDeliveryApiKey("<get this from Promoted.ai>")
    .withMetricsEndpoint("<get this from Promoted.ai>")
    .withMetricsApiKey("<get this from Promoted.ai>")
    .withWarmup(true)
    .build();
```

### Client Configuration Parameters

| Name                           | Type                                                           | Description                                                                                                                                                                                                                                                                               |
| ------------------------------ | -------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `deliveryEndpoint`               | String                                                      | API endpoint for Promoted.ai's Delivery API                                                                                                                                         |
| `deliveryApiKey`               | String                                                      | API key used in the `x-api-key` header for Promoted.ai's Delivery API                                                                                                                                         |
| `deliveryTimeoutMillis`        | long                                                         | Timeout on the Delivery API call. Defaults to 250.                                                                                                                                                                                                                                        |
| `metricsEndpoint`               | String                                                      | API endpoint for Promoted.ai's Metrics API                                                                                                                                         |
| `metricsApiKey`               | String                                                      | API key used in the `x-api-key` header for Promoted.ai's Metrics API                                                                                                                                         |
| `metricsTimeoutMillis`        | long                                                         | Timeout on the Metrics API call. Defaults to 3000.                                                                                                                                                                                                                                        |
| `warmup`        | boolean                                                         | Option to warm up the HTTP connection pool at initialization.                                                                                                                                                                                                                                        |
| `metricsExecutor`        | Executor                                                         | Metrics API calls take place on an `ExecutorService`. You are expected to supply one that suits your application requirements.                                                                                                                                                                                                                                        |
| `applyTreatmentChecker`         | ApplyTreatmentChecker | Optional function interface called during delivery, accepts an experiment and returns a boolean indicating whether the request should be considered part of the control group (false) or in the treatment arm of an experiment (true). If not set, the default behavior of checking the experiement `arm` is applied. |
| `maxRequestInsertions`        | int                                                         | Maximum number of request insertions that will be passed to (and returned from) Delivery API. Defaults to 1000.                                                                                                                                                                                                                                        |
| `shadowTrafficDeliveryRate`    | Number between 0 and 1                                         | rate = [0,1] of traffic that gets directed to Delivery API as "shadow traffic". Only applies to cases where Delivery API is not called. Defaults to 0 (no shadow traffic).                                                                                                                                                               |
| `blockingShadowTraffic`      | boolean                           | Option to make shadow traffic a blocking (as opposed to background) call to delivery API, defaults to False.        

## Data Types

### UserInfo

Basic information about the request user.
Field Name | Type | Optional? | Description
---------- | ---- | --------- | -----------
`userId` | String | Yes | The platform user id, cleared from Promoted logs.
`anonUserId` | String | Yes | A different user id (presumably a UUID) disconnected from the platform user id (e.g. an "anonymous user id"), good for working with unauthenticated users or implementing right-to-be-forgotten.
`isInternalUser` | boolean | Yes | If this user is a test user or not, defaults to false.

---

### CohortMembership

Useful fields for experimentation during the delivery phase.
Field Name | Type | Optional? | Description
---------- | ---- | --------- | -----------
`arm` | String | Yes | 'CONTROL' or one of the TREATMENT values ('TREATMENT', 'TREATMENT1', etc.).
`cohortId` | String | Yes | Name of the cohort (e.g. "LOCAL_HOLDOUT" etc.)

---

### Properties

Properties bag. Can create using a `Map<String, Object>`. Has the JSON structure:

```json
  "struct": {
    "product": {
      "id": "product3",
      "title": "Product 3",
      "url": "www.mymarket.com/p/3"
      // other key-value pairs...
    }
  }
```

---

### Insertion

Content being served at a certain position in a response.  The same type is used for the input (Request Insertion) and output (Response Insertion).

Field Name | Type | Optional? | Description
---------- | ---- | --------- | -----------
`userInfo` | UserInfo | Yes | The user info structure.
`insertionId` | String | Yes | Identifier for the Insertion.  insertionId is always returned on the Response Insertion (generated by either the SDK or API).  Do not pass this in on Request Insertions unless Promoted engineers say otherwise.  If set on the Request Insertion, the insertion ID will usually be returned on Response Insertions.  This is not guaranteed.
`contentId` | String | No | Identifier for the content to be ranked, must be set.
`retrievalRank` | Integer | Yes | Optional original ranking of this content item.
`retrievalScore` | Float | Yes | Optional original quality score of this content item.
`properties` | Properties | Yes | Any additional custom properties to associate. For v1 integrations, it is fine not to fill in all the properties.

---

### Size

User's screen dimensions.
Field Name | Type | Optional? | Description
---------- | ---- | --------- | -----------
`width` | Integer | No | Screen width
`height` | Integer | No | Screen height

---

### Screen

State of the screen including scaling.
Field Name | Type | Optional? | Description
---------- | ---- | --------- | -----------
`size` | Size | Yes | Screen size
`scale` | Float | Yes | Current screen scaling factor

---

### ClientHints

Alternative to user-agent strings. See https://raw.githubusercontent.com/snowplow/iglu-central/master/schemas/org.ietf/http_client_hints/jsonschema/1-0-0
Field Name | Type | Optional? | Description
---------- | ---- | --------- | -----------
`isMobile` | Boolean | Yes | Mobile flag
`brand` | ClientBrandHint[] | Yes |
`architecture` | String | Yes |
`model` | String | Yes |
`platform` | String | Yes |
`platformVersion` | String | Yes |
`uaFullVersion` | String | Yes |

---

### ClientBrandHint

See https://raw.githubusercontent.com/snowplow/iglu-central/master/schemas/org.ietf/http_client_hints/jsonschema/1-0-0
Field Name | Type | Optional? | Description
---------- | ---- | --------- | -----------
`brand` | String | Yes | Mobile flag
`version` | String | Yes |

---

### Location

Information about the user's location.
Field Name | Type | Optional? | Description
---------- | ---- | --------- | -----------
`latitude` | Float | No | Location latitude
`longitude` | Float | No | Location longitude
`accuracyInMeters` | Integer | Yes | Location accuracy if available

---

### Browser

Information about the user's browser.
Field Name | Type | Optional? | Description
---------- | ---- | --------- | -----------
`user_agent` | String | Yes | Browser user agent string
`viewportSize` | Size | Yes | Size of the browser viewport
`clientHints` | ClientHints | Yes | HTTP client hints structure

---

### Device

Information about the user's device.
Field Name | Type | Optional? | Description
---------- | ---- | --------- | -----------
`deviceType` | one of (`UNKNOWN_DEVICE_TYPE`, `DESKTOP`, `MOBILE`, `TABLET`) | Yes | Type of device
`brand` | String | Yes | "Apple, "google", Samsung", etc.
`manufacturer` | String | Yes | "Apple", "HTC", Motorola", "HUAWEI", etc.
`identifier` | String | Yes | Android: android.os.Build.MODEL; iOS: iPhoneXX,YY, etc.
`screen` | Screen | Yes | Screen dimensions
`ipAddress` | String | Yes | Originating IP address
`location` | Location | Yes | Location information
`browser` | Browser | Yes | Browser information

---

### Paging

Describes a page of insertions
Field Name | Type | Optional? | Description
---------- | ---- | --------- | -----------
`size` | Number | Yes | Size of the page being requested
`offset` | Number | Yes | Page offset

---

### Request

A request for content insertions.
Field Name | Type | Optional? | Description
---------- | ---- | --------- | -----------
`userInfo` | UserInfo | Yes | The user info structure.
`requestId` | String | Yes | Generated by the SDK when needed (_do not set_)
`useCase` | String | Yes | One of the use case enum values or strings, i.e. 'FEED', 'SEARCH', etc.
`properties` | Properties | Yes | Any additional custom properties to associate.
`paging` | Paging | Yes | Paging parameters
`device` | Device | Yes | Device information (as available)
`disablePersonalization` | Boolean | Yes | If true, disables personalized inputs into Delivery algorithm.

---

### DeliveryRequest

Input to `deliver`, returns ranked insertions for display.
Field Name | Type | Optional? | Description
---------- | ---- | --------- | -----------
`request` | Request | No | The underlying request for content, including all candidate insertions with content ids.
`experiment` | CohortMembership | Yes | A cohort to evaluation in experimentation.
`onlyLog` | Boolean | Yes | Defaults to false. Set to true to log the request as the CONTROL arm of an experiment, not call Delivery API, but rather deliver paged insertions from the request.
`retrievalInsertionOffset` | int | Yes |   Start index in the request insertions in the list of ALL insertions. See [Pages of Request Insertions](#pages-of-request-insertions) for more details.
---

### DeliveryResponse

Output of `deliver`, includes the ranked insertions for you to display.
Field Name | Type | Optional? | Description
---------- | ---- | --------- | -----------
`response` | Response | No | The reponse from Delivery API, which includes the insertions. These are from Delivery API (when `deliver` was called, i.e. we weren't either only-log or part of an experiment) or the input insertions (when the other conditions don't hold).
`clientRequestId` | String | Yes | Client-generated request id sent to Delivery API and may be useful for logging and debugging. You may fill this in yourself if you have a suitable id, otherwise the SDK will generate one.
`executionServer` | one of 'API' or 'SDK' | Yes | Indicates if response insertions on a delivery request came from the API or the SDK.

---

### PromotedDeliveryClient

| Method              | Input           | Output         | Description                                                                                                                                                                                                                                                                                                                                 |
| ------------------- | --------------- | -------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `deliver`           | DeliveryRequest | DeliveryResponse | Makes a request (subject to experimentation) to Delivery API for insertions, which are then returned.                                                                                                                                                                                                               |

---

## Calling the Delivery API

Let's say the previous code looks like this:

```java
void getProducts(ProductRequest req) {
  List<Product> products = ...; // Logic to get products from DB, apply filtering, etc.
  sendSuccessToClient(products);
}
```

We might modify to something like this:

```java
void getProducts(ProductRequest req) {
  List<Product> products = ...;
  Request req = new Request().userInfo(new UserInfo()
      .anonUserId("12355")
      .paging(new Paging().size(100).offset(0)));
  DeliveryRequest deliveryRequest = new DeliveryRequest(req);
  Map<String, Product> productsMap = new HashMap<>();
  for (Product product : products) {
      deliveryRequest.getRequest().addInsertion(new Insertion().contentId(product.getProductId());
      productsMap.put(product.getProductId(), product);
  }

  DeliveryResponse response = promotedDeliveryClient.deliver(deliveryRequest);
  List<Product> rankedProducts = new ArrayList<>();
  for (Insertion responseInsertion : response.getResponse().getInsertion()) {
      rankedProducts.add(productsMap.get(responseInsertion.getContentId());
  }

  sendSuccessToClient(rankedProducts);
}
```

## Pages of Request Insertions

Clients can send a subset of all request insertions to Promoted in Delivery API's `request.insertion` array. The `retrievalInsertionOffset` property specifies the start index of the array `request.insertion` in the list of ALL request insertions.

`request.paging.offset` should be set to the zero-based position in ALL request insertions (_not_ the relative position in the `request.insertion` array).

Examples

- If there are 10 items and all 10 items are in `request.insertion`, then insertion_start=0.
- If there are 10,000 items and the first 500 items are on `request.insertion`, then retrievalInsertionOffset=0.
- If there are 10,000 items and we want to send items [500,1000) on `request.insertion`, then retrievalInsertionOffset=500.
- If there are 10,000 items and we want to send the last page [9500,10000) on `request.insertion`, then retrievalInsertionOffset=9500.

`retrievalInsertionOffset` is required to be less than `paging.offset` or else a `ValueError` will result.

Additional details: https://docs.promoted.ai/docs/ranking-requests#sending-even-more-request-insertions

## Logging only
You can use `deliver` but add a `onlyLog: true` parameter.

### Position

- Do not set the insertion `position` field in client code. The SDK and Delivery API will set it when `deliver` is called.

### Experiments

Promoted supports the ability to run Promoted-side experiments.  Sometimes it is useful to run an experiment in your where `promoted-java-delivery-client` is integrated (e.g. you want arm assignments to match your own internal experiment arm assignments).

```java
// Create a small config indicating the experiment is a 50-50 experiment where 10% of the users are activated.
TwoArmExperiment experimentConfig = TwoArmExperiment.create5050TwoArmExperimentConfig("promoted-v1", 5, 5);

void getProducts(ProductRequest req) {
  List<Product> products = //...;

  // This gets the anonymous user id from the request.
  String anonUserId = getAnonUserId(req);
  CohortMembership experimentMembership = experimentConfig.checkMembership(anonUserId);

  Request req = new Request().userInfo(new UserInfo()
      .anonUserId("12355")
      .paging(new Paging().size(100).offset(0)));

  // If experimentActivated can be false (e.g. only 5% of users get put into an experiment) and
  // you want the non-activated behavior to not call Delivery API, then you need to specify onlyLog to false.
  // This is common during ramp up.  `onlyLog` can be dropped if it's always false.
  //
  // Example:
  // `onlyLog: experimentMembership == null`
  DeliveryRequest deliveryRequest = new DeliveryRequest(req, experimentMembership);

  DeliveryResponse response = promotedDeliveryClient.deliver(deliveryRequest);
  //...
}
```

Here's an example using custom arm assignment logic (not using `twoArmExperimentConfig5050`).

```java
  // If you already use an experiment framework, it'll have the ability to return
  // (1) if a user is activated into an experiment and
  // (2) which arm to perform.
  //
  CohortMembership experimentMembership = null;
  if (isUserActivated(experimentName, anonUserId)) {
  	boolean inTreatment = isUserInTreatmentArm(experimentName, anonUserId);
  	
    // Only log if the user is activated into the experiment.
    experimentMembership = new CohortMembership().cohortId(experimentName)
        .arm(inTreatment ? CohortArm.TREATMENT : CohortArm.CONTROL);
  }
```

## When there is ranking logic after the SDK's `deliver` method.

It is strongly advised against implementing ranking logic after the deliver method.  Promoted will perform suboptimally and certain production functions will be broken:
- The Delivery API will not have access to the final position, which can affect the performance of third-stage ranking and the Blender.
- While asynchronous logging of positions back to Promoted is supported, the join rate is not guaranteed to be 100%. This makes addressing position biases challenging.
- Introspection reports will not be accurate.

For optimal results with Promoted's SDK, all ranking logic should be done before the `deliver` method.  Here's an example flow in a listing API call:
1. The Controller retrieves candidates.
2. It ranks the results and formulates ML features.
3. It invokes the `deliver` method from Delivery SDK. This method takes care of when the Delivery API should be called.  If Delivery API is not called, the SDK pages the input candidate list.  The call handles experiments, logging and shadow traffic.
4. The Controller enriches the paged response with detailed listing information.
5. Finally, the Controller sends the enriched SDK response back to the client.

However, in some scenarios, the ideal flow may not be followed due to reasons such as:
- The Delivery API is being employed for partial ranking, and there's further ranking logic after its call. Nevertheless, Promoted still requires the final positions to be recorded as an SDK DeliveryLog.
- The Controller doesn't possess a uniform code path through the ranking system. For instance, embedding the Delivery API call within conditional logic might mean that the SDK DeliveryLog isn't consistently invoked.

Promoted works best when Promoted knows the final ranking.  When the actual response differs, the client should send fallback SDK `DeliveryLog`.  The inner methods can be accessed and called directly:
1. `plan(...)` and `prepareRequest(...)` can be used to set up the request.
2. `if (plan.useApiResponse()) { callDeliveryAPI(...) }` can be used to optionally call Delivery API.
3. `handleSdkAndLog(...)` handles logging the final ranking, handles paging, and shadow traffic.

Example
```java
DeliveryRequest deliveryRequest = ...;
DeliveryPlan plan = client.plan(deliveryRequest);
client.prepareRequest(deliveryRequest, plan);

// The following could happen in a different method or server.
Response apiResponse = null;
if (plan.useApiResponse()) {
  try {
    apiResponse = client.callDeliveryAPI(deliveryRequest);
  } catch (DeliveryException ex) {
    LOGGER.log(Level.WARNING, "Error calling Delivery API, falling back", ex);
  }
}

// ... Extra ranking logic.  This is strongly discouraged.

DeliveryRequest modifiedDeliveryRequest = ... /* with a different insertion ordering. */;
return client.handleSdkAndLog(modifiedDeliveryRequest, plan, apiResponse);
```

See the main `deliver` method for another example.

# Improving this library
- Source code following the [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)

## Tech used

Uses

- Java 11
- Maven 3
- Unit tests with JUnit 5 Jupiter.

## Scripts

- Test: `mvn test`
- Build and install SNAPSHOT locally: `mvn install`

## Deploy
Follow the auth and gpg instructions on [sonatype.org](https://central.sonatype.org/publish/publish-maven/).  Dan wasn't able to get the `maven-release-plugin` to work.

1. Update the version in `PromotedDeliveryClient.java`.
2. Create a SNAPSHOT release.
- Update the version for a SNAPSHOT or non-SNAPSHOT release: `mvn versions:set -DnewVersion=x.y.z-SNAPSHOT`.
- Sign: `mvn clean verify -P release -Dgpg.keyname=0x<EIGHT HEX DIGITS OF YOUR GPG KEY>`.  For the GPG Key, use this command to get the hex version: `gpg --list-signatures --keyid-format 0xshort`.  It's fine to create a new GPG key if needed.
- Deploy to Sonatype: `mvn deploy -P release -Dgpg.keyname=0x<EIGHT HEX DIGITS OF YOUR GPG KEY>`.
- Verify the release looks correct in [Sonatype](https://s01.oss.sonatype.org/#view-repositories;staging~browsestorage)

3. Create a non-SNAPSHOT.
- Follow similar steps to 1.  Change to `mvn versions:set -DnewVersion=x.y.z`
- Go to [Sonatype](https://s01.oss.sonatype.org/#view-repositories;staging~browsestorage).  In the left nav, click on "Staging Repositories" under "Build Promotion" section.  Use "Close" and "Release" steps for deploying.  There can be delay in the staging repository showing up in the list.  Also click "Refresh".
- Send a PR for the updated `pom.xml` file to the next patched SNAPSHOT version.  This can be a little misleading and confusing if the next release is not a patch change.
