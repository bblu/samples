// <<OpenGL Programming Guide 9th Endition>>
//  main.cpp
//  particalbyfeedback
//
//  Created by lu wenbo on 2018/6/16.
//  Copyright © 2018 lu wenbo. All rights reserved.
//

#include <iostream>
#include <GL/glew.h>
#include <GLFW/glfw3.h>

void display(void)
{
    glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    glClear(GL_COLOR_BUFFER_BIT);
    glBegin(GL_TRIANGLES);
    {
        glColor3f(1.0,0.0,0.0);
        glVertex2f(0, .5);
        glColor3f(0.0,1.0,0.0);
        glVertex2f(-.5,-.5);
        glColor3f(0.0, 0.0, 1.0);
        glVertex2f(.5, -.5);
    }
    glEnd();
}

int main(int argc, const char * argv[]) {
    if(!glfwInit()){
        return -1;
    }
    GLFWwindow* win = glfwCreateWindow(320, 240, "OpenGL Triangles - bblu", NULL, NULL);
    if(!glewInit()){
        return -1;
    }
    glfwMakeContextCurrent(win);
    while(!glfwWindowShouldClose(win)){
        display();
        glfwSwapBuffers(win);
        glfwPollEvents();
    }
    glfwTerminate();
    return 0;
}
