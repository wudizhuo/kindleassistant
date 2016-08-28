require 'calabash-android/calabash_steps'

When(/^I enter url "(.*?)"$/) do |arg1|
  element = "android.widget.EditText id:'et_user_url'"
    query(element, setText: '')
    enter_text(element, arg1)
    hide_soft_keyboard
end
