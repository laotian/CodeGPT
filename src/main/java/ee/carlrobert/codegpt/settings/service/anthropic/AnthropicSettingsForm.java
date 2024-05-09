package ee.carlrobert.codegpt.settings.service.anthropic;

import static ee.carlrobert.codegpt.credentials.CredentialsStore.CredentialKey.ANTHROPIC_API_KEY;

import com.intellij.ui.components.JBPasswordField;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;
import com.intellij.util.ui.UI;
import ee.carlrobert.codegpt.CodeGPTBundle;
import ee.carlrobert.codegpt.credentials.CredentialsStore;
import ee.carlrobert.codegpt.ui.UIUtil;
import javax.swing.JPanel;
import org.jetbrains.annotations.Nullable;

public class AnthropicSettingsForm {

  private final JBPasswordField apiKeyField;
  private final JBTextField apiVersionField;
  private final JBTextField modelField;

  public AnthropicSettingsForm(AnthropicSettingsState settings) {
    apiKeyField = new JBPasswordField();
    apiKeyField.setColumns(30);
    apiKeyField.setText(CredentialsStore.getCredential(ANTHROPIC_API_KEY));
    apiVersionField = new JBTextField(settings.getApiVersion(), 35);
    modelField = new JBTextField(settings.getModel(), 35);
  }

  public JPanel getForm() {
    return FormBuilder.createFormBuilder()
        .addComponent(UI.PanelFactory.grid()
            .add(UI.PanelFactory.panel(apiKeyField)
                .withLabel(CodeGPTBundle.get("settingsConfigurable.shared.apiKey.label"))
                .resizeX(false)
                .withComment(
                    CodeGPTBundle.get("settingsConfigurable.service.anthropic.apiKey.comment"))
                .withCommentHyperlinkListener(UIUtil::handleHyperlinkClicked))
            .add(UI.PanelFactory.panel(apiVersionField)
                .withLabel(CodeGPTBundle.get("shared.apiVersion"))
                .withComment(CodeGPTBundle.get(
                    "settingsConfigurable.service.anthropic.apiVersion.comment"))
                .withCommentHyperlinkListener(UIUtil::handleHyperlinkClicked)
                .resizeX(false))
            .add(UI.PanelFactory.panel(modelField)
                .withLabel(CodeGPTBundle.get("settingsConfigurable.shared.model.label"))
                .withComment(CodeGPTBundle.get(
                    "settingsConfigurable.service.anthropic.model.comment"))
                .resizeX(false))
            .createPanel())
        .addComponentFillVertically(new JPanel(), 0)
        .getPanel();
  }

  public AnthropicSettingsState getCurrentState() {
    var state = new AnthropicSettingsState();
    state.setModel(modelField.getText());
    state.setApiVersion(apiVersionField.getText());
    return state;
  }

  public void resetForm() {
    var state = AnthropicSettings.getCurrentState();
    apiKeyField.setText(CredentialsStore.getCredential(ANTHROPIC_API_KEY));
    apiVersionField.setText(state.getApiVersion());
    modelField.setText(state.getModel());
  }

  public @Nullable String getApiKey() {
    var apiKey = new String(apiKeyField.getPassword());
    return apiKey.isEmpty() ? null : apiKey;
  }
}
