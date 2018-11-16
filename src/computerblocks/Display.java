package computerblocks;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;
import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Display {
  private long window;

  Display() {
    GLFWErrorCallback.createPrint(System.err).set();
    if (!glfwInit()) throw new IllegalStateException("Unable to initialize GLFW");

    glfwDefaultWindowHints();
    glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
    glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

    window = glfwCreateWindow(1280, 720, "computer-blocks", NULL, NULL);
    if (window == NULL)throw new RuntimeException("Failed to create the GLFW window");

    glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) glfwSetWindowShouldClose(window, true);
      if (key == GLFW_KEY_L && action == GLFW_PRESS) glfwSetWindowSize(window, 100, 100);
		});

    // glfwSetWindowSizeCallback(window, this::windowSizeCallback);

    try ( MemoryStack stack = stackPush() ) {
			IntBuffer pWidth = stack.mallocInt(1); // int*
			IntBuffer pHeight = stack.mallocInt(1); // int*
			glfwGetWindowSize(window, pWidth, pHeight);

			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			glfwSetWindowPos(
				window,
				(vidmode.width() - pWidth.get(0)) / 2,
				(vidmode.height() - pHeight.get(0)) / 2
			);
		}

		glfwMakeContextCurrent(window);
		glfwSwapInterval(1);
		glfwShowWindow(window);

    GL.createCapabilities();
    glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
  }

  public void draw() {
    if (!glfwWindowShouldClose(window)) {
      glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
      glBegin(GL_QUADS);
        glColor3f(1.0f, 0.439f, 0.439f);
        glVertex2f(-0.5f, 0.5f);
        glVertex2f(0.5f, 0.5f);
        glVertex2f(0.5f, -0.5f);
        glVertex2f(-0.5f, -0.5f);
      glEnd();
      glfwSwapBuffers(window);
			glfwPollEvents();
    } else {
      destroy();
    }
  }

  public void destroy() {
    glfwFreeCallbacks(window);
		glfwDestroyWindow(window);
		glfwTerminate();
		glfwSetErrorCallback(null).free();
  }
}