/* Copyright 2019 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package iam.snippets;

// [START iam_disable_service_account]

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.iam.v1.Iam;
import com.google.api.services.iam.v1.IamScopes;
import com.google.api.services.iam.v1.model.DisableServiceAccountRequest;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

public class DisableServiceAccount {

  // Disables a service account.
  public static void disableServiceAccount(String projectId, String serviceAccountName)
      throws GeneralSecurityException, IOException {
    // String projectId = "my-project-id";
    // String serviceAccountName = "my-service-account-name";

    Iam service = initService();

    String serviceAccountEmail = serviceAccountName + "@" + projectId + ".iam.gserviceaccount.com";
    DisableServiceAccountRequest request = new DisableServiceAccountRequest();
    service
        .projects()
        .serviceAccounts()
        .disable("projects/-/serviceAccounts/" + serviceAccountEmail, request)
        .execute();

    System.out.println("Disabled service account: " + serviceAccountEmail);
  }

  private static Iam initService() throws GeneralSecurityException, IOException {
    // Use the Application Default Credentials strategy for authentication. For more info, see:
    // https://cloud.google.com/docs/authentication/production#finding_credentials_automatically
    GoogleCredentials credential =
        GoogleCredentials.getApplicationDefault()
            .createScoped(Collections.singleton(IamScopes.CLOUD_PLATFORM));
    // Initialize the IAM service, which can be used to send requests to the IAM API.
    Iam.Builder serviceBuilder = new Iam.Builder(GoogleNetHttpTransport.newTrustedTransport(),
        GsonFactory.getDefaultInstance(),
        new HttpCredentialsAdapter(credential));

    return serviceBuilder
        .setApplicationName("service-accounts")
        .build();
  }
}
// [END iam_disable_service_account]
