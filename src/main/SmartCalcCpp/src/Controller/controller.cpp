#include "controller.h"

namespace s21 {
Controller* Controller::instance = nullptr;

Controller::Controller() { model_ = new Model; }

Controller::~Controller() { delete model_; }

bool Controller::Calculate(const char* input_expression, long double x) {
    bool return_value = model_->MainFunction(input_expression, x);
    if (return_value) {
        result_ = model_->GetResult();
    }
    return return_value;
}

bool Controller::CheckExpressionCorrectness(const char* input_expression) {
    return model_->CheckExpressionCorrectness(input_expression);
}

long double Controller::GetResult() { return result_; }
}  // namespace s21
